package com.progark.gameofwits.models

import com.progark.gameofwits.model.Lobby
import model.User
import org.junit.Assert
import org.junit.Test

class LobbyTest {
    @Test
    fun testJoinLobby() {
        val player = User("user_id_1", "bob", true, true)
        val player2 = User("user_id_2", "jon", true, true)
        val lobby = Lobby("id123","0123", false, player.id, true)
        lobby.join(player2)
        Assert.assertTrue(lobby.players.contains(player2))
        val players = lobby.players
        lobby.join(player2)
        // Check that user is not added again
        Assert.assertEquals(lobby.players, players)
    }

    @Test
    fun testHost() {
        val player = User("user_id_1", "bob", true, true)
        val lobby = Lobby("id123","0123", false, player.id, true)
        lobby.join(player)
        lobby.isHost(player.id)
    }
}