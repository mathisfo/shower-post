package com.progark.gameofwits.observers

import model.User

interface PlayerObserver {
    fun update(event: String, payload: Any?)
}

object PlayerEventSource {
    private var observers = mutableListOf<PlayerObserver>()

    private fun notifyObservers(event: String, payload: Any?) {
        observers.forEach { observer -> observer.update(event, payload) }
    }

    fun addObserver(observer: PlayerObserver) {
        observers += observer
    }

    fun playerJoined(user: User) {
        notifyObservers("PLAYER_JOINED", user)
    }

    fun allUsersSubmitted() {
        notifyObservers("ALL_USERS_SUBMITTED", null)
    }

    fun gameCreated(id: String) {
        notifyObservers("GAME_CREATED", id)
    }
}
