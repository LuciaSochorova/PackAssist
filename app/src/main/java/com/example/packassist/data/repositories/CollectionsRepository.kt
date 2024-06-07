package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing collections and items.
 *
 */
interface CollectionsRepository {
    /**
     * Updates an existing collection.
     *
     * @param collection The collection to update.
     */
    suspend fun updateCollection(collection: Collection)

    /**
     * Inserts a new collection.
     *
     * @param collection The collection to insert.
     * @return The row ID of the newly inserted collection.
     */
    suspend fun insertCollection(collection: Collection): Long

    /**
     * Deletes a collection.
     *
     * @param collection The collection to delete.
     */
    suspend fun deleteCollection(collection: Collection)


    /**
     * Upsert items for a given collection.
     *
     * @param collection The collection to upsert items for.
     * @param items The items to upsert.
     */
    suspend fun upsertItemsOfCollection(collection: Collection, items: List<Item>)

    /**
     * Gets a list of all collections with their associated items for a given event.
     *
     * @param eventId The ID of the event.
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    fun getEventCollectionsWithItemsStream(eventId: Int): Flow<List<ItemsOfCollection>>

    /**
     *Gets the items for a given collection.
     *
     * @param id The ID of the collection.
     * @return An ItemsOfCollection object containing the collection and its items.
     */
    suspend fun getItemsOfCollection(id: Int): ItemsOfCollection

    /**
     * Gets a list of all collections with their associated items that are not associated with any event.
     *
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    fun getAllNoEventCollectionsWithItems(): Flow<List<ItemsOfCollection>>

    /**
     * Gets the ID of a collection based on its row ID.
     *
     * @param rowId The row ID of the collection.
     * @return The ID of the collection.
     */
    suspend fun getCollectionId(rowId: Long): Int

    /**
     * Gets the event ID associated with a collection.
     *
     * @param id The ID of the collection.
     * @return The ID of the event, or null if the collection is not associated with an event.
     */
    suspend fun getCollectionEvent(id: Int): Int?
}