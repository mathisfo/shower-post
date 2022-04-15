package storage

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Letters
import com.progark.gameofwits.model.Lobby
import com.progark.gameofwits.model.Player
import com.progark.gameofwits.storage.documents.GameDoc
import com.progark.gameofwits.storage.documents.UserDoc
import kotlinx.coroutines.tasks.await


class Storage private constructor(val db: FirebaseFirestore) : Repository {
    companion object {
        private var instance: Storage? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Storage(Firebase.firestore).also { instance = it }
        }
    }

    override fun getUser(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getLobbyID(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getLobbies(): List<Lobby> {
        val snapshot = db.collection("lobbies").get().await()
        val lobbies = snapshot.map { doc ->
            val id = doc.id
            val pin = doc.getString("pin")
            val active = doc.getBoolean("active")!!
            val active_round = doc.getDouble("active_round")
            val hostName = doc.getString("hostName")

            val lobby: Lobby = Lobby(id, pin!!, active, active_round!!, hostName!!, mutableListOf(
                Player("","", false)
            ))
            lobby
        }
        return lobbies
    }

    override suspend fun getLobby(id: String): Lobby {
        println("GETLOBBY ID " + id)
        val doc = db.collection("lobbies").document(id).get().await()

        val players = db.collection("lobbies").document("Q3GHIMfcUh7wndWpA3bn").collection("players").document("mathias").get().await()

        val playersArray: MutableList<Player?> = mutableListOf(players.toObject(Player::class.java))

        println("ARRAY: " + playersArray)


        val lobby = Lobby(doc.id, doc.getString("pin")!!, doc.getBoolean("active")!!, doc.getDouble("active_round")!!, doc.getString("hostName")!!, playersArray)

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
        val game = doc.toObject(GameDoc::class.java)
        val lettersDoc = db.collection("LetterArrays").document().get().await()
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
                "words.turn" + turn + "." + userID, word
            ).await()
    }
    
    override suspend fun createLobbyAndAddToStore(lobby: Lobby):String {
        val ref = this.db.collection("lobbies")
            .add(lobby).await()
        val doc = ref.get().await().toObject(Lobby::class.java)
        println("Lobby: " + doc)
        return doc!!.id
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
        val doc = db.collection("lobbies").whereEqualTo("pin", PIN).get().await().documents[0]


        val lobby = Lobby(
            doc.id, doc.getString("pin")!!,
            doc.getBoolean("active")!!,
            doc.getDouble("active_round")!!,
            doc.getString("hostName")!!,
            mutableListOf(Player("","", false))
        )

        return lobby;
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
