package com.example.packassist.data.entitiesAndDaos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date


@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String? = null,
    val date : Date? = null,
    val notes: String? = null
)

data class EventsCollections(
    @Embedded val event : Event,
    @Relation(
        parentColumn = "id",
        entityColumn = "event"
    ) val collections : List<Collection>
)