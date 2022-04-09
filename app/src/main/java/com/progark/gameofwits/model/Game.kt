package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.j2objc.annotations.Property

data class Game(
    @DocumentId val id: String,
    @PropertyName("CurrentTurn") val CurrentTurn: Int,
    @PropertyName("NumberOfTurns") val NumberOfTurns: Int,
) {
    constructor(): this("", 0, 0)
}