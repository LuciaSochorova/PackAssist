package com.example.packassist.data

import java.time.LocalDate

data class Event(
    val id: Int = 0,
    val name: String,
    val location: String?,
    val date: LocalDate?,
    val notes: String?
)