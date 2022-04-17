package storage

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.observers.PlayerEventSource
import com.progark.gameofwits.storage.documents.GameDoc
import com.progark.gameofwits.storage.documents.LobbyDoc
import com.progark.gameofwits.storage.documents.PlayerRealtime
import com.progark.gameofwits.storage.documents.UserDoc
import kotlinx.coroutines.tasks.await
import model.User


class Storage private constructor(val db: FirebaseFirestore, val realtime: DatabaseReference) :
    Repository {
    companion object {
        private var instance: Storage? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Storage(Firebase.firestore, Firebase.database.reference).also {
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
            Lobby(lobbyDoc.id!!, lobbyDoc.pin!!, lobbyDoc.active!!, mutableListOf())
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
            mutableListOf()
        )
        return lobby
    }

    override suspend fun createUser(name: String): String {
        val deviceId = FirebaseInstallations.getInstance().id.await()
        val user = UserDoc("", Timestamp.now())
        db.collection("users").document(deviceId).set(user).await()
        return deviceId
    }

    override suspend fun getGame(id: String): GameDoc {
        val doc = db.collection("games").document(id).get().await()
        val game = doc.toObject(GameDoc::class.java)
        val lettersDoc = db.collection("LetterArrays").document().get().await()
        return game!!
    }

    override suspend fun addGameToFirebase(game: GameDoc): String? {
        val ref = this.db.collection("games")
            .add(game).await()
        val doc = ref.get().await().toObject(GameDoc::class.java)
        return doc!!.id
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
            LobbyDoc(null, lobby.pin, lobby.active, listOf(), hostRef)
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
}
