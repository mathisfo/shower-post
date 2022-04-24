package model


data class User(
    val id: String,
    val name: String,
    val ready: Boolean,
    val submitted: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return id === other.id
        }
        return false
    }
}