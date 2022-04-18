package com.progark.gameofwits.observers

import model.User

interface Observer {
    fun update(event: String, payload: Any?)
}

object PlayerEventSource {
    private var observers = mutableListOf<Observer>()

    private fun notifyObservers(event: String, payload: Any?) {
        observers.forEach { observer -> observer.update(event, payload) }
    }

    fun addObserver(observer: Observer) {
        observers += observer
    }

    fun playerJoined(user: User) {
        notifyObservers("PLAYER_JOINED", user)
    }
}
