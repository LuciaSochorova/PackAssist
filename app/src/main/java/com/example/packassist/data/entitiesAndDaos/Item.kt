package com.example.packassist.data.entitiesAndDaos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


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
    val packet : Boolean = false
)
