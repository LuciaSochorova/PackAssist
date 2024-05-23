package com.example.packassist.data.entitiesAndDaos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String?,
    val date: LocalDate?,
    val notes: String?
)

data class EventsCollections(
    @Embedded val event : Event,
    @Relation(
        parentColumn = "id",
        entityColumn = "event"
    ) val collections : List<Collection>
)