import com.soywiz.korge.*
import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.*
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import controller.GameScene
import controller.MenuController

suspend fun main() = Korge(Korge.Config(module = GameModule))

object GameModule : Module() {
    override val mainScene = MenuController::class
    override val bgcolor = RGBA(253, 247, 240)
    override val size = SizeInt(1080, 1920)
    override val windowSize = SizeInt(1080, 1920)

    override suspend fun AsyncInjector.configure() {
        mapInstance(Dependency("HELLO WORLD"))
        mapPrototype { GameScene(get()) }
        mapPrototype { MenuController(get()) }
    }
}

class Dependency(val value: String)
