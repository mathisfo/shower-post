package com.progark.gameofwits.model

class Lobby(val id: String, val active: Boolean, val users: List<String>) {
    override fun toString(): String {
        return "Lobby($id): $users"
    }
}

