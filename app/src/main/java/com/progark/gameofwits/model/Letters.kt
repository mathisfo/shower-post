package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class Letters (
    @PropertyName("turn1") val turn1: List<String>,
    @PropertyName("turn2") val turn2: List<String>?,
    @PropertyName("turn3") val turn3: List<String>?,
    @PropertyName("turn4") val turn4: List<String>?,
    @PropertyName("turn5") val turn5: List<String>?,
    ) {
        constructor(): this(listOf(), null, null, null,null)
    }