package controller

import view.MenuView

class Menu2Controller(private val menuView: MenuView) {
    fun getView(): MenuView {
        return menuView
    }
}