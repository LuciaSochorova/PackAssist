package com.example.packassist.data.entitiesAndDaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Update()
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Upsert
    suspend fun upsert(item: Item)
    @Query("DELETE FROM items where id = :id")
    suspend fun deleteItem(id : Int)

    @Query("SELECT * FROM items where id = :id")
    fun getItem(id: Int): Flow<Item?>

    @Query("SELECT * FROM items where collection = :collectionId")
    suspend fun getItemsOfCollection(collectionId: Int): List<Item>

}