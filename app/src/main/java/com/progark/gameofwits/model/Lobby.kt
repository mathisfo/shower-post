package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import model.User

class Lobby(
    val id: String,
    val pin: String,
    val active: Boolean,
    val players: MutableList<User>,
    ) {
}



