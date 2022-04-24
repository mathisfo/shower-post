package com.progark.gameofwits.models

import com.progark.gameofwits.model.Game
import com.progark.gameofwits.model.Round
import org.junit.Assert.assertEquals
import org.junit.Test

class GameTest {
    @Test
    fun correctInitializationOfRound() {
        val round = Round("aabc", mapOf())
        assertEquals(round.availableLetter['a'], 2)
        assertEquals(round.availableLetter['b'], 1)
        assertEquals(round.availableLetter['c'], 1)
        assertEquals(round.availableLetter['d'], null)
    }
    @Test
    fun testCorrectScore() {
        val rounds = listOf(
            Round("dacr", mapOf("user1" to "car", "user2" to "card")),
            Round("enif", mapOf("user1" to "fin", "user2" to "fine")))
        val game = Game("id123", rounds,2,2, mutableMapOf(),true)
        game.calculateScore()
        assertEquals(game.scores["user1"], 6)
        assertEquals(game.scores["user2"], 8)
        assertEquals(game.scores["user3"], null)
    }
}