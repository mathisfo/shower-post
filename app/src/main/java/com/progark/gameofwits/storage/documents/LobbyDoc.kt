package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

class LobbyDoc(
    @DocumentId val id: String?,
    @PropertyName("pin") val pin: String?,
    @PropertyName("active") val active: Boolean?,
    @PropertyName("games") val games: List<DocumentReference>?,
    @PropertyName("host") val host: DocumentReference?,
    @PropertyName("started") val started: Boolean
) {
    constructor() : this("", "", false, null, null, false)
}