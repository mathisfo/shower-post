package storage

import com.progark.gameofwits.model.Lobby

interface Repository {
    fun getUser(): String

    fun addLobbyToFirestore(lobby: HashMap<String, Any>)
    suspend fun getLobbyID(): String
    // fun getActiveLobbies(): List<Lobby>
    // fun getLobbyById(id: String): Lobby
    suspend fun getLobbies(): List<Lobby>
    suspend fun getLobby(id: String): Lobby

}
