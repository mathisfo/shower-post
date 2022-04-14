package storage

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.installations.FirebaseInstallations

import com.google.firebase.firestore.ktx.getField

import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Letters
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.storage.documents.GameDoc
import com.progark.gameofwits.storage.documents.UserDoc
import kotlinx.coroutines.tasks.await
import model.User
class Storage private constructor(val db: FirebaseFirestore) : Repository {
    companion object {
        private var instance: Storage? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Storage(Firebase.firestore).also { instance = it }
        }
    }

    override fun getUser(): String {
        // TODO: get user from firebase
        return ""
    }

    override fun addLobbyToFirestore(lobby: HashMap<String, Any>) {
        this.db.collection("lobbies")
            .add(lobby)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    override suspend fun getLobbyID(): String {
        return "lobbyID"
    }

    override suspend fun getLobbies(): List<Lobby> {
        val snapshot = db.collection("lobbies").get().await()
        val lobbies = snapshot.map { doc ->
            val id = doc.id
            val active = doc.getBoolean("active")!!
            val lobby = Lobby(id, active, listOf())
            lobby
        }
        return lobbies
    }

    override suspend fun getLobby(id: String): Lobby {
        val doc = db.collection("lobbies").document(id).get().await()
        val lobby = Lobby(doc.id, doc.getBoolean("active")!!, doc.get("users") as List<String>)
        print(lobby)
        return lobby
    }


    override suspend fun createUser(name: String) {
        val deviceId = FirebaseInstallations.getInstance().id.await()
        val user = UserDoc("", name)
        db.collection("users").document(deviceId).set(user).await()
        println("ADDED NEW USER")
    }

    override suspend fun getGame(id: String): GameDoc {
        val doc = db.collection("games").document(id).get().await()
        println("DOC: " + doc)
        val game = doc.toObject(GameDoc::class.java)
        println("Game: " + game)
        return game!!
    }

    override suspend fun addGameToFirebase(game: GameDoc):String? {
        val ref = this.db.collection("games")
            .add(game).await()
        val doc = ref.get().await().toObject(GameDoc::class.java)
        return doc!!.id
    }

    override suspend fun addWordToFirebase(userID: String, word: String, turn: Int, gameID: String) {
        this.db.collection("games").document(gameID)
            .update(
                "words.turn"+turn+"."+userID, word
            ).await()
    }

    /**
    override suspend fun getLobbyID(): String {
        val db = Firebase.firestore
        val lobbies = db.collection("lobbies").get()
        if (!lobbies.isEmpty()) {
            println(lobbies)
            return lobbies.last().id
        }
        return "øhø"
    }
    **/
}