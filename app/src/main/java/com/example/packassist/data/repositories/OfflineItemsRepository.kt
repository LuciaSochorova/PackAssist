package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemDao

/**
 * Offline repository for managing items.
 *
 * @property itemDao The DAO for accessing the item table.
 * @constructor Create empty Offline items repository
 */
class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    /**
     * Updates an existing item.
     *
     * @param item The item to update.
     */
    override suspend fun updateItem(item: Item) = itemDao.update(item)

    /**
     * Delete item
     *
     * @param item The item to delete.
     */
    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    /**
     * Deletes an item based on its ID.
     *
     * @param itemId The ID of the item to delete.
     */
    override suspend fun deleteItem(itemId: Int) = itemDao.deleteItem(itemId)

    /**
     * Upsert an item
     *
     * @param item The item to upsert.
     */
    override suspend fun upsertItem(item: Item) = itemDao.upsert(item)

    /**
     * Gets a list of items for a given collection.
     *
     * @param collectionId The ID of the collection.
     * @return A list of Item objects.
     */
    override suspend fun getItemsOfCollection(collectionId: Int): List<Item> =
        itemDao.getItemsOfCollection(collectionId)
}