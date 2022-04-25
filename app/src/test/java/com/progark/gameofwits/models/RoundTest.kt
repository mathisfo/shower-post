package com.progark.gameofwits.models

import com.progark.gameofwits.model.Round
import org.junit.Assert
import org.junit.Test

class RoundTest {
    @Test
    fun correctInitializationOfRound() {
        val round = Round("aabc", mapOf())
        Assert.assertEquals(round.availableLetter['a'], 2)
        Assert.assertEquals(round.availableLetter['b'], 1)
        Assert.assertEquals(round.availableLetter['c'], 1)
        Assert.assertEquals(round.availableLetter['d'], null)
    }

    @Test
    fun testValidLetters() {
        val round = Round("card", mapOf())
        val valid = round.isValidLetters("car")
        Assert.assertTrue(valid)
    }

    @Test
    fun testInvalidLetters() {
        val round = Round("car", mapOf())
        val valid = round.isValidLetters("card")
        Assert.assertFalse(valid)
    }
}