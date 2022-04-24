package com.progark.wordwar.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

class RoundItem(val letters: String? = "", val answers: Map<String, String>? = null){}

data class GameDoc(
    @DocumentId
    val id: String?,
    @PropertyName("currentRound")
    val currentRound: Int?,
    @PropertyName("maxRounds")
    val maxRounds: Int?,
    @PropertyName("scores")
    val scores: Map<String, Int>?,
    @PropertyName("rounds")
    val rounds: Map<String, RoundItem>?
) {
    constructor() : this("",0, 0, null, null)
}