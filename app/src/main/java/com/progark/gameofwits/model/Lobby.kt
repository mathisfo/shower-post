package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

class Lobby(@DocumentId val id: String, @PropertyName("active") val active: Boolean, @PropertyName("active_round") val active_round: Double, @PropertyName("hostName") val hostName: String) {
    constructor(): this("", false, 0.0, "DefaultHoster" )

    override fun toString(): String {
        return "Lobby($id): $hostName"
    }
}



