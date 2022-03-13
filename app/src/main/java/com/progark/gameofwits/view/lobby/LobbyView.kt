package com.progark.gameofwits.view.lobby

import Dependency
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korio.async.launchImmediately


class LobbyView(dependency: Dependency) : Scene() {


    override suspend fun Container.sceneInit(): Unit {
        uiButton(200.0, 150.0) {
            text = "Host"
            position(views.virtualWidth / 2, views.virtualHeight / 2)
            onClick { launchImmediately {
                sceneContainer.changeTo<LobbyHostView>()
            }

            }

        }

        uiButton(200.0, 150.0) {
            text = "Join"
            position(views.virtualWidth / 2, views.virtualHeight / 2 - 120)

        }
    }


}