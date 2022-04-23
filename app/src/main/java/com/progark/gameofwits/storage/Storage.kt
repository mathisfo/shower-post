package storage

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.model.Round
import com.progark.gameofwits.model.createRandomLetters
import com.progark.gameofwits.observers.PlayerEventSource
import com.progark.gameofwits.storage.documents.*
import kotlinx.coroutines.tasks.await
import model.User


class Storage private constructor(
    val db: FirebaseFirestore,
    val realtime: DatabaseReference,
) :
    Repository {
    companion object {
        private var instance: Storage? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Storage(
                Firebase.firestore,
                Firebase.database.reference,
            ).also {
                instance = it
            }
        }
    }


    override suspend fun getUserId(): String {
        return FirebaseInstallations.getInstance().id.await()
    }

    override suspend fun getLobbies(): List<Lobby> {
        val snapshot = db.collection("lobbies").get().await()
        val lobbies = snapshot.map { doc ->
            val lobbyDoc = doc.toObject(LobbyDoc::class.java)
            Lobby(lobbyDoc.id!!, lobbyDoc.pin!!, lobbyDoc.active!!, lobbyDoc.host!!.id)
        }
        return lobbies
    }

    override suspend fun getLobby(id: String): Lobby {
        val snapshot = db.collection("lobbies").document(id).get().await()
        val doc = snapshot.toObject(LobbyDoc::class.java)!!
        val lobby = Lobby(
            doc.id!!,
            doc.pin!!,
            doc.active!!,
            doc.host!!.id,
        )
        return lobby
    }

    override suspend fun createUser(): String {
        val deviceId = FirebaseInstallations.getInstance().id.await()
        val user = UserDoc("", Timestamp.now())
        db.collection("users").document(deviceId).set(user).await()
        return deviceId
    }

    override suspend fun addGameToFirebase(game: GameDoc): String? {
        val ref = this.db.collection("games")
            .add(game).await()
        val doc = ref.get().await().toObject(GameDoc::class.java)
        return ref.id
    }

    override suspend fun addWordToFirebase(
        userID: String,
        word: String,
        turn: Int,
        gameID: String
    ) {
        this.db.collection("games").document(gameID)
            .update(
                "words.turn" + turn + "." + userID, word
            ).await()
    }

    override suspend fun createLobby(lobby: Lobby, hostId: String): String {
        val hostRef = this.db.document("users/$hostId")
        val lobbyData =
            LobbyDoc(null, lobby.pin, lobby.active, listOf(), hostRef, lobby.started)
        val doc = this.db.collection("lobbies").add(lobbyData).await()
        return doc.id
    }

    override suspend fun openLobby(lobbyId: String, pin: String) {
        this.realtime.child("lobbies").child(pin).setValue(lobbyId).await()
    }

    override suspend fun joinLobbyWithPin(
        userId: String,
        username: String,
        lobbyPIN: String
    ): String {
        val snapshot = this.realtime.child("lobbies").child(lobbyPIN).get().await()
        // TODO: Error handling
        if (!snapshot.exists()) return ""
        val lobbyId = snapshot.value.toString()
        this.realtime.child("players").child(lobbyId).child(userId)
            .setValue(mapOf("ready" to false, "submitted" to false, "name" to username)).await()
        return lobbyId
    }

    override suspend fun createGame(lobby: Lobby, max_rounds: Int): String {
        val answers = mutableMapOf<String, String>()
        lobby.players.forEach { player -> answers[player.id] = "" }
        val rounds = mutableMapOf<String, RoundItem>()
        for (i in 1..max_rounds) {
            rounds["$i"] = RoundItem(createRandomLetters(), answers)
        }
        val game = GameDoc("", 1, max_rounds, mapOf(), rounds)
        val ref = this.db.collection("games").add(game).await()
        this.db.collection("lobbies").document(lobby.id).update(
            mapOf(
                "started" to true,
                "games" to FieldValue.arrayUnion(ref)
            )
        )
        return ref.id
    }

    override suspend fun updateAnswerToUser(game: Game, userId: String, word: String) {
        this.db.collection("games").document(game.id).update(
            mapOf(
                "rounds.${game.current_round}.answers.${userId}" to word
            )
        ).await()
    }

    override suspend fun getGame(id: String): Game {
        val snapshot = this.db.collection("games").document(id).get().await()
        val game = snapshot.toObject(GameDoc::class.java)!!
        val rounds = game.rounds!!.values.map { round -> Round(round.letters!!, round.answers!!) }
        return Game(snapshot.id, rounds, game.currentRound!!, game.maxRounds!!, game.scores!!)
    }

    override suspend fun updateCurrentRound(id: String) {
        val game = this.getGame(id)
        this.db.collection("games").document(id).update("currentRound", game.current_round + 1)
    }


    override fun listenToLobby(lobbyId: String) {
        val snapshot = this.realtime.child("players").child(lobbyId)
        val playerListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val values = snapshot.getValue<Map<String, PlayerRealtime>>()!!
                for (key in values.keys) {
                    val data = values[key]!!
                    val user = User(key, data.name!!, data.ready!!, data.submitted!!)
                    PlayerEventSource.playerJoined(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.w(TAG, "loadUsers:onCanceled", error.toException())
            }
        }
        snapshot.addValueEventListener(playerListener)
    }

    override fun listenToAnswers(game: Game) {
        this.db.collection("games").document(game.id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val gameSnap = snapshot.toObject(GameDoc::class.java)!!
                val round = gameSnap.rounds!!["${game.current_round}"]
                println(round)
                var submitted = 0;
                round!!.answers!!.values.forEach { word -> if (word != "") submitted++ }
                println("Words submitted: " + submitted)
                PlayerEventSource.userHasSubmitted(submitted)
            }
        }
    }

    override fun listenOnLobbyForGames(lobbyId: String) {
        this.db.collection("lobbies").document(lobbyId).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val lobbySnap = snapshot.toObject(LobbyDoc::class.java)
                if (lobbySnap != null && lobbySnap.started) {
                    val games = lobbySnap.games ?: return@addSnapshotListener
                    println(games)
                    println(lobbyId)
                    if (!games.isEmpty()) {
                        PlayerEventSource.gameCreated(games.last().id)
                    }
                }
            }
        }
    }

    override fun listenToNextRound(gameID: String, currentRound: Int) {
        this.db.collection("games").document(gameID).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val gameSnap = snapshot.toObject(GameDoc::class.java)!!
                if (gameSnap.currentRound == currentRound+1) {
                    println("Go to next round")
                    PlayerEventSource.goToNextRound(gameSnap.currentRound)
                }
            }
        }
    }

    override suspend fun loadValidWords(): List<String> {
        val storage = FirebaseStorage.getInstance().reference
        val hm = storage.child("words.txt")
        val ONE_MEGA_BYTE: Long = 1024 * 1024
        val words = String(hm.getBytes(ONE_MEGA_BYTE).await())
        return words.split("\n")
    }
}
