package com.example.packassist.data.entitiesAndDaos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String?,
    val date: LocalDate?,
    val notes: String?
)