package com.progark.gameofwits.controller

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import storage.Repository

class  LobbyController() {
    val db = Firebase.firestore;

    fun addLobby() {
        val lobby = hashMapOf(
            "active" to true,
            "lobbies" to listOf<String>("Anhkha", "Bernt"),
        )
    }

    fun lobbyMembers(id : String): HashMap<String, String> {
        var hashMap : HashMap<String, String> = HashMap<String, String> ()

        hashMap.put("1", "Mathias Fossum")
        hashMap.put("2", "Johanne Fossum")
        return hashMap;
    }


}