package com.example.packassist.data.entitiesAndDaos

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "collections",
    foreignKeys = [
    androidx.room.ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["event"],
        onDelete = androidx.room.ForeignKey.CASCADE
    )
])
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    @ColumnInfo(index = true)
    val event : Int? = null
)

data class ItemsOfCollection(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "collection"
    )
    val items : List<Item?>
)
