package com.example.packassist.data.entitiesAndDaos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


/**
 * Represents an item.
 *
 * @property id The unique identifier for the item.
 * @property name The name of the item.
 * @property collection The ID of the collection the item belongs to.
 * @property packed Whether the item is packed or not.
 * @constructor Create empty Item
 */
@Entity (tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = Collection::class,
            parentColumns = ["id"],
            childColumns = ["collection"],
            onDelete = ForeignKey.CASCADE
        )
    ] )
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    @ColumnInfo(index = true)
    val collection : Int,
    val packed : Boolean = false
)
