package com.example.packassist.data.entitiesAndDaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

/**
 * Data Access Object for the Item entity.
 *
 */
@Dao
interface ItemDao {

    /**
     * Updates an existing item.
     *
     * @param item The item to update.
     */
    @Update()
    suspend fun update(item: Item)

    /**
     * Deletes an item.
     *
     * @param item The item to delete.
     */
    @Delete
    suspend fun delete(item: Item)

    /**
     * Upsert an item.
     *
     * @param item The item to upsert.
     */
    @Upsert
    suspend fun upsert(item: Item)

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     */
    @Query("DELETE FROM items where id = :id")
    suspend fun deleteItem(id : Int)

    /**
     * Gets all items for a given collection.
     *
     * @param collectionId The ID of the collection.
     * @return A list of items.
     */
    @Query("SELECT * FROM items where collection = :collectionId")
    suspend fun getItemsOfCollection(collectionId: Int): List<Item>

}