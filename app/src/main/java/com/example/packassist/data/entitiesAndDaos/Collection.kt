package com.example.packassist.data.entitiesAndDaos

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


/**
 * A data class representing a collection of items.
 *
 * @property id The unique identifier for the collection.
 * @property name The name of the collection.
 * @property event The ID of the event associated with the collection.
 * @constructor Create empty Collection
 */
@Entity(
    tableName = "collections",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["event"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    @ColumnInfo(index = true)
    val event: Int? = null
)

/**
 * A data class representing a collection of items and its associated items.
 *
 * @property collection The collection object.
 * @property items The list of items in the collection.
 * @constructor Create empty Items of collection
 */
data class ItemsOfCollection(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "collection"
    )
    val items: List<Item>
)
