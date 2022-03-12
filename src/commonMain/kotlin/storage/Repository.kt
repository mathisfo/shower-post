package storage

interface Repository {
    fun getUser(): String
}

// Access a Cloud Firestore instance from your Activity

val db = Firebase.firestore

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