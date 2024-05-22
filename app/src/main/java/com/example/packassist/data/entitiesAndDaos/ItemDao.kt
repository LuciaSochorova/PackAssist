package com.example.packassist.data.entitiesAndDaos

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

interface ItemDao {

    @Update()
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Upsert
    suspend fun upsert(item: Item)

    @Query("SELECT * FROM items where id = :id")
    fun getItem(id: Int): kotlinx.coroutines.flow.Flow<Item>
}