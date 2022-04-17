package com.progark.gameofwits.storage.documents

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class UserDoc(
    @DocumentId var id: String?,
    @PropertyName("registered_at") var registeredAt: Timestamp?
) {
    constructor() : this("", null)
}