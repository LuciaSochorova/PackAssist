package com.example.packassist.data.entitiesAndDaos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date


/**
 * Represents an event.
 *
 * @property id The unique identifier for the event.
 * @property name The name of the event.
 * @property location The location of the event.
 * @property date The date of the event.
 * @property notes Any additional notes about the event.
 * @constructor Create empty Event
 */
@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String? = null,
    val date: Date? = null,
    val notes: String? = null
)

/**
 * Represents a collection of events and their associated collections.
 *
 * @property event The event object.
 * @property collections The list of collections associated with the event.
 * @constructor Create empty Events collections
 */
data class EventsCollections(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "id",
        entityColumn = "event"
    ) val collections: List<Collection>
)