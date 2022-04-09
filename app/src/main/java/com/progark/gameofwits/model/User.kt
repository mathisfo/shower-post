package model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    @DocumentId  var id: String,
    @PropertyName("name") val name: String = "") {
}