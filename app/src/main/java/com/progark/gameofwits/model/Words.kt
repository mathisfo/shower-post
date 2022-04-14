package com.progark.gameofwits.model

import com.google.firebase.firestore.PropertyName

class Words (
    @PropertyName("turn1") val turn1: Map<String, String>,
    @PropertyName("turn2") val turn2: Map<String, String>?,
    @PropertyName("turn3") val turn3: Map<String, String>?,
    @PropertyName("turn4") val turn4: Map<String, String>?,
    @PropertyName("turn5") val turn5: Map<String, String>?,
) {
    constructor(): this(emptyMap<String, String>(), null, null, null,null)
}
