package com.progark.gameofwits.view.lobby

import Dependency

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korio.file.std.resourcesVfs

class LobbyHostView(dependency: Dependency) : Scene() {

    override suspend fun Container.sceneInit(): Unit {
        val font: BitmapFont = resourcesVfs["clear_sans.fnt"].readBitmapFont()
        text("Title", 500.0, font = font, color = Colors.BLACK ) {
            
        }

        uiButton(200.0, 150.0) {
            text = "Play"
            position(views.virtualWidth / 2, views.virtualHeight / 2)
            onClick {

            }

        }
    }
}