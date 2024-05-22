package com.example.packassist.data.entitiesAndDaos

import androidx.room.Entity
import androidx.room.ForeignKey
import kotlin.collections.Collection

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Collection::class,
            parentColumns = ["id"],
            childColumns = ["idColl"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["idColl"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["idColl", "idItem" ]
)
data class CollectionsItems(
    val idItem: Int,
    val idColl: Int,
    val packed: Boolean = false
)
