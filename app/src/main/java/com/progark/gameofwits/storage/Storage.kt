package storage

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.ContentValues.TAG
import com.progark.gameofwits.model.Lobby
import kotlinx.coroutines.tasks.await

class Storage : Repository {
    override fun getUser(): String {
        // TODO: get user from firebase
        return ""
    }

    override fun addLobbyToFirestore(lobby: HashMap<String, Any>) {
        val db = Firebase.firestore
        db.collection("lobbies")
            .add(lobby)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    override suspend fun getLobbyID(): String {
        val db = Firebase.firestore
        val lobbies = db.collection("lobbies").get().await()
        if (!lobbies.isEmpty) {
            println(lobbies)
            return lobbies.last().id
        }
        return "øhø"
    }
}
