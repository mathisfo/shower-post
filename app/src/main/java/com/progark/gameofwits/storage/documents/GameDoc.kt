package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.progark.gameofwits.model.Letters

data class GameDoc(
    @DocumentId var id: String?,
    @PropertyName("lobbyID") val lobbyID: String?,
    @PropertyName("currentTurn") val currentTurn: Int?,
    @PropertyName("numberOfTurns") val numberOfTurns: Int?,
    @PropertyName("letterArrays") val letterArrays: Letters?,
) {
    constructor(): this(null, null, null, null, null)
}