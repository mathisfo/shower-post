package model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    val id: String,
    val name: String,
    val ready: Boolean,
    val submitted: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            val user2 = other
            return id === user2.id
        }
        return false
    }
}