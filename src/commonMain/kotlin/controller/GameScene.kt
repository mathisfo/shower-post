package controller

import Dependency
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.vector.roundRect

class GameScene(val dependency: Dependency) : Scene() {

    override suspend fun Container.sceneInit() {

        println(dependency.value)
        val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
        val cellSize = views.virtualWidth / 5.0
        val fieldSize = 50 + 4 * cellSize
        val leftIndent = (views.virtualWidth - fieldSize) / 2
        val topIndent = 300.0
        val bgField = roundRect(fieldSize, fieldSize, 5.0, fill = Colors["#b9aea0"]) {
            position(leftIndent, topIndent)
        }
        graphics {
            position(leftIndent, topIndent)
            fill(Colors["#cec0b2"]) {
                for (i in 0..3) {
                    for (j in 0..3) {
                        roundRect(10 + (10 + cellSize) * i, 10 + (10 + cellSize) * j, cellSize, cellSize, 5.0)
                    }
                }
            }
        }
        val bgLogo = roundRect(cellSize, cellSize, 5.0, fill = Colors["#edc403"]) {
            position(leftIndent, 30.0)
        }
        text("2048", cellSize * 0.5, Colors.WHITE, font).centerOn(bgLogo)
        val bgBest = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = Colors["#bbae9e"]) {
            alignRightToRightOf(bgField)
            alignTopToTopOf(bgLogo)
        }
        val bgScore = roundRect(cellSize * 1.5, cellSize * 0.8, 5.0, fill = Colors["#bbae9e"]) {
            alignRightToLeftOf(bgBest, 24)
            alignTopToTopOf(bgBest)
        }
        text("BEST", cellSize * 0.25, RGBA(239, 226, 210), font) {
            centerXOn(bgBest)
            alignTopToTopOf(bgBest, 5.0)
        }
        text("0", cellSize * 0.5, Colors.WHITE, font) {
            setTextBounds(Rectangle(0.0, 0.0, bgBest.width, cellSize - 24.0))
            alignment = TextAlignment.MIDDLE_CENTER
            alignTopToTopOf(bgBest, 12.0)
            centerXOn(bgBest)
        }
        text("SCORE", cellSize * 0.25, RGBA(239, 226, 210), font) {
            centerXOn(bgScore)
            alignTopToTopOf(bgScore, 5.0)
        }
        text("0", cellSize * 0.5, Colors.WHITE, font) {
            setTextBounds(Rectangle(0.0, 0.0, bgScore.width, cellSize - 24.0))
            alignment = TextAlignment.MIDDLE_CENTER
            centerXOn(bgScore)
            alignTopToTopOf(bgScore, 12.0)
        }
    }
}