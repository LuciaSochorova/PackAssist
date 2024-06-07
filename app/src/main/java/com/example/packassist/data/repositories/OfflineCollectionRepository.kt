package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.CollectionDao
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import kotlinx.coroutines.flow.Flow

/**
 * Offline repository for managing collections and items.
 *
 * @property collectionDao The DAO for accessing the collection table.
 * @constructor Create empty Offline collection repository
 */
class OfflineCollectionRepository(private val collectionDao : CollectionDao) : CollectionsRepository{
    /**
     * Update collection
     *
     * @param collection The collection to update.
     */
    override suspend fun updateCollection(collection: Collection) {
        collectionDao.update(collection)
    }

    /**
     * Inserts a new collection.
     *
     * @param collection The collection to insert.
     * @return The row ID of the newly inserted collection.
     */
    override suspend fun insertCollection(collection: Collection) : Long = collectionDao.insert(collection)


    /**
     * Delete a collection
     *
     * @param collection The collection to delete.
     */
    override suspend fun deleteCollection(collection: Collection) {
        collectionDao.delete(collection)
    }

    /**
     * Upsert items for a given collection.
     *
     * @param collection The collection to upsert items for.
     * @param items The items to upsert.
     */

    override suspend fun upsertItemsOfCollection(collection: Collection, items: List<Item>) {
        collectionDao.upsertItemsOfCollection(collection, items)
    }

    /**
     * Gets a Flow of a list of ItemsOfCollection objects for a given event.
     *
     * @param eventId The ID of the event.
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    override fun getEventCollectionsWithItemsStream(eventId : Int): Flow<List<ItemsOfCollection>> = collectionDao.getEventCollectionsWithItems(eventId)

    /**
     * Gets an ItemsOfCollection object for a given collection.
     *
     * @param id The ID of the collection.
     * @return An ItemsOfCollection object containing the collection and its items.
     */
    override suspend fun getItemsOfCollection(id: Int): ItemsOfCollection = collectionDao.getItemsOfCollection(id)

    /**
     * Gets a Flow of a list of ItemsOfCollection objects for collections that are not associated with any event.
     *
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    override fun getAllNoEventCollectionsWithItems(): Flow<List<ItemsOfCollection>> = collectionDao.getAllNoEventCollectionsWithItems()

    /**
     * Gets the ID of a collection based on its row ID.
     *
     * @param rowId The row ID of the collection.
     * @return The ID of the collection.
     */
    override suspend fun getCollectionId(rowId: Long): Int = collectionDao.getCollectionId(rowId)

    /**
     * Gets the event ID associated with a collection.
     *
     * @param id The ID of the collection.
     * @return The ID of the event, or null if the collection is not associated with an event.
     */
    override suspend fun getCollectionEvent(id : Int) : Int? = collectionDao.getCollectionEvent(id)
}