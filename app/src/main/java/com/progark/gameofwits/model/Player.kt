package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

class Player(
    @DocumentId val
    id: String,

    @PropertyName("playerName")
    val name: String,

    @PropertyName("ready")
    val ready: Boolean,



    ) {
    constructor(): this("", "mathias", false)

    override fun toString(): String {
        return "Lobby($id): $name"
    }
}