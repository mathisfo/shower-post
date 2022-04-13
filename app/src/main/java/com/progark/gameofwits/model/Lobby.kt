package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

class Lobby(
    @DocumentId val
    id: String,


    @PropertyName("pin")
    val pin: String,

    @PropertyName("active")
    val active: Boolean,

    @PropertyName("active_round")
    val active_round: Double,

    @PropertyName("hostName") val hostName: String,

    @PropertyName("players") val players: List<Player>,

    ) {



    constructor(): this("", "0000",false, 0.0, "DefaultHoster", mutableListOf(Player("", "DefaultName", false)))

    override fun toString(): String {
        return "Lobby($id): $hostName"
    }
}



