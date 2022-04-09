package com.progark.gameofwits.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName

data class Letters (
    @DocumentId val id: String,
    @PropertyName("Turn1") val Turn1: List<String>,
    @PropertyName("Turn2") val Turn2: List<String>?,
    @PropertyName("Turn3") val Turn3: List<String>?,
    @PropertyName("Turn4") val Turn4: List<String>?,
    @PropertyName("Turn5") val Turn5: List<String>?,
    ) {
        constructor(): this("", listOf(), null, null, null,null)
    }