package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import model.User



class Lobby(
    val id: String,
    val pin: String,
    val active: Boolean,
    val host: String,
) {
    val players = mutableListOf<User>()


    fun join(player: User) {
        if (!players.contains(player)) {
            players.add(player)
        }
    }

    fun isHost(userId: String): Boolean {
        return userId == host
    }
}



