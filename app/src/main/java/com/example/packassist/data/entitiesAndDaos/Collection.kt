package com.example.packassist.data.entitiesAndDaos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val event : Int? = null
)

data class ItemsOfCollection(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "collection"
    )
    val items : List<Item>
)
