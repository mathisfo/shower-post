package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class RoundDoc(
    @DocumentId val id: String?,
    @PropertyName("answers") val answers: Map<String, String>?,
    @PropertyName("letters") val letters: String?
) {
    constructor() : this("", null, null)
}