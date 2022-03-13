package com.progark.gameofwits.view

import Dependency
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.textSize
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position


class LobbyView(dependency: Dependency) : Scene() {


    override suspend fun Container.sceneInit(): Unit {
        uiButton(200.0, 150.0) {
            text = "Host"
            textSize = 14.0;
            position(views.virtualWidth / 2, views.virtualHeight / 2)
            onClick {  }

        }

        uiButton(200.0, 150.0) {
            text = "Join"
            position(views.virtualWidth / 2, views.virtualHeight / 2 - 120)

        }
    }


}