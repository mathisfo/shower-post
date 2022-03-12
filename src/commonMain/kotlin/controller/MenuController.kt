package controller

import Dependency
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.roundRect
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.vector.roundRect

class MenuController(dependency: Dependency) : Scene() {
    override suspend fun Container.sceneInit() {
        val button = roundRect(50.0, 50.0, 5.0, fill = Colors.RED) {
            position(views.virtualWidth / 2, views.virtualHeight / 2)
        }
        button.onClick {
            launchImmediately {
                sceneContainer.changeTo<GameScene>("...loading")
            }
        }
    }
}