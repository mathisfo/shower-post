package com.progark.gameofwits.storage.documents

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName
import com.google.j2objc.annotations.Property
import com.progark.gameofwits.model.Player

class LobbyDoc(
    @DocumentId val id: String?,
    @PropertyName("pin") val pin: String?,
    @PropertyName("active") val active: Boolean?,
    @PropertyName("games") val games: List<DocumentReference>?,
    @PropertyName("host") val host: DocumentReference?,
) {
    constructor() : this("", "", false, null, null)
}