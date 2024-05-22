package com.example.packassist.data.entitiesAndDaos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = ""
)
