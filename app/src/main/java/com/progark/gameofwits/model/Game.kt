package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class Game(
    @DocumentId val id: String,
    @PropertyName("CurrentTurn") val CurrentTurn: Int,
    @PropertyName("NumberOfTurns") val NumberOfTurns: Int,
    @PropertyName("LetterArrays") val LetterArrays: String,
    var Letters: Letters?,
) {
    constructor(): this("", 0, 0, "", null)
}