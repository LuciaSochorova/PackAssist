package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Item

/**
 * Repository interface for managing items.
 *
 */
interface ItemsRepository {
    /**
     * Updates an existing item.
     *
     * @param item The item to update.
     */
    suspend fun updateItem(item: Item)

    /**
     * Deletes an item.
     *
     * @param item The item to delete.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Deletes an item by its ID.
     *
     * @param itemId The ID of the item to delete.
     */
    suspend fun deleteItem(itemId: Int)

    /**
     * Upsert an item
     *
     * @param item The item to upsert.
     */
    suspend fun upsertItem(item: Item)

    /**
     * Gets a list of items associated with a collection.
     *
     * @param collectionId The ID of the collection.
     * @return A list of Item objects.
     */
    suspend fun getItemsOfCollection(collectionId: Int): List<Item>
}