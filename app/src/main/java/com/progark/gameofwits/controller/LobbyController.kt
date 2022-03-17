package com.progark.gameofwits.controller

import com.progark.gameofwits.model.Lobby
import storage.Repository


class LobbyController(private val repo: Repository) {
    fun addLobby() {
        val lobby = hashMapOf(
            "active" to true,
            "lobbies" to listOf<String>("Anhkha", "Bernt"),
        )

        repo.addLobbyToFirestore(lobby)
    }
    suspend fun getID(lobby: Lobby) {
        try {
            repo.getLobbyID()
        } catch (e: Exception) {
            println(e)
        }
    }
}