import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.*
import com.soywiz.korim.text.TextAlignment
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.vector.roundRect
import com.soywiz.korma.interpolation.Easing
import storage.Storage

suspend fun main() = Korge(width = 512, height = 824, bgcolor = RGBA(253, 247, 240)) {
    //val storage = Storage()
    //val user = storage.getUser()
    //println(user)
    val font = resourcesVfs["clear_sans.fnt"].readBitmapFont()
    val cellSize = views.virtualWidth / 5.0
    val fieldSize = 50 + 4 * cellSize
    val leftIndent = (views.virtualWidth - fieldSize) / 2
    val topIndent = 150.0
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
    populate()
}


fun populate() {

    val db = Firebase.firestore
// Access a Cloud Firestore instance from your Activity

// Create a new user with a first and last name
    val user = hashMapOf(
            "first" to "Postmann",
            "last" to "Katt",
            "died" to 1944
    )

// Add a new document with a generated ID
    db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
}
