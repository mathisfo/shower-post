package com.progark.gameofwits.models

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
}