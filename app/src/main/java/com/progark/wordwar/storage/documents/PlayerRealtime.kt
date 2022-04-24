package com.progark.wordwar.storage.documents

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlayerRealtime(
    val name: String? = null,
    val ready: Boolean? = null,
    val submitted: Boolean? = null
) {
}