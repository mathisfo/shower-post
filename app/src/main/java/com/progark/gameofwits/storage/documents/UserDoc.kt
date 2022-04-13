package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class UserDoc(
    @DocumentId var id: String?,
    @PropertyName("name") val name: String?,
) {
}