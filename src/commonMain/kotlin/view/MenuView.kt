package view

import Dependency
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately
import controller.GameScene
import controller.MenuController

class MenuView(dependency: Dependency) : Scene() {
    private val controller = MenuController()

    override suspend fun Container.sceneInit(): Unit {
        solidRect(100.0, 100.0, Colors.BLUE) {
            position(views.virtualWidth / 2, views.virtualHeight / 2)
            onClick {
                launchImmediately {
                    sceneContainer.changeTo<GameScene>()
                }
                controller.editUser("2")
            }
        }
    }
}