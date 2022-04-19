package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class GameDoc(
    @PropertyName("current_round")
    val currentRound: Int?,
    @PropertyName("max_rounds")
    val maxRounds: Int?,
    @PropertyName("scores")
    val scores: Map<String, Int>?,
) {
    constructor() : this(0, 0, null)
}