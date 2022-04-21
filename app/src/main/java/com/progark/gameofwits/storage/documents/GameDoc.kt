package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.PropertyName
import com.progark.gameofwits.model.Letters

data class RoundItem(val letters: String, val answers: Map<String, String>)

data class GameDoc(
    @PropertyName("current_round")
    val currentRound: Int?,
    @PropertyName("max_rounds")
    val maxRounds: Int?,
    @PropertyName("scores")
    val scores: Map<String, Int>?,
    @PropertyName("rounds")
    val rounds: List<RoundItem>?
) {
    constructor() : this(0, 0, null, null)
}