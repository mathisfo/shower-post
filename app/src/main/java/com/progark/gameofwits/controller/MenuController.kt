package controller

import model.User

class MenuController {
    private var user = User("0")

    fun handleUserEdit(id: String) {
        this.user = User(id)
    }

}