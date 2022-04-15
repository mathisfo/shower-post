package storage

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.model.Player
import com.progark.gameofwits.storage.documents.GameDoc
import com.progark.gameofwits.storage.documents.LobbyDoc
import com.progark.gameofwits.storage.documents.UserDoc
import kotlinx.coroutines.tasks.await
import model.User


class Storage private constructor(val db: FirebaseFirestore, val realtime: FirebaseDatabase) :
    Repository {
    companion object {
        private var instance: Storage? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Storage(Firebase.firestore, Firebase.database).also { instance = it }
        }
    }

    override fun getUser(): String {
        TODO("Not yet implemented")
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
        val players = doc.players!!.map { ref ->
            val playerDoc = ref.get().await()
            playerDoc.toObject(UserDoc::class.java)!!
        }
        val lobby = Lobby(
            doc.id!!,
            doc.pin!!,
            doc.active!!,
            players.map { userDoc -> User(userDoc.id!!, userDoc.name!!) }.toMutableList()
        )
        return lobby
    }

    override suspend fun createUser(name: String): String {
        val deviceId = FirebaseInstallations.getInstance().id.await()
        val user = UserDoc("", name)
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
            LobbyDoc(null, lobby.pin, lobby.active, listOf(), hostRef, mutableListOf(hostRef))
        val doc = this.db.collection("lobbies").add(lobbyData).await()
        return doc.id
    }


    override suspend fun joinLobbyWithName(name: String, lobbyPIN: String) {

        val data = hashMapOf(
            // TODO: fetch the username and use that instead
            "playerName" to "Mathias",
            "ready" to false,
        )

        println("FØR JOIN")
        this.db.collection("lobbies/vdWrz94eJ9Fe0Vhxl5M9/players").add(data).await();
        print("ETTER JOIN")

    }

    override suspend fun getLobbyByPIN(PIN: String): Lobby {
        TODO();
        val doc = db.collection("lobbies").whereEqualTo("pin", PIN).get().await().documents[0]
    }
}
