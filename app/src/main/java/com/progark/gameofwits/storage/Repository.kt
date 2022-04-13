package storage

import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Lobby
import model.User

interface Repository {
    fun getUser(): String

    fun addLobbyToFirestore(lobby: HashMap<String, Any>)
    suspend fun getLobbyID(): String
    // fun getActiveLobbies(): List<Lobby>
    // fun getLobbyById(id: String): Lobby
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby
    suspend fun createUser(name: String)
    suspend fun getGame(id: String): Game
    fun addGameToFirestore(game: HashMap<String, Any>)

}
