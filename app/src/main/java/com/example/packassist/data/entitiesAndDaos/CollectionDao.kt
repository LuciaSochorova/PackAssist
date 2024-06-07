package com.example.packassist.data.entitiesAndDaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Collection entity.
 *
 */
@Dao
interface CollectionDao {
    /**
     * Inserts a new collection into the database.
     *
     * @param collection The collection to insert.
     * @return The row ID of the newly inserted collection.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    /**
     * Updates an existing collection in the database.
     *
     * @param collection The collection to update.
     */
    @Update
    suspend fun update(collection: Collection)

    /**
     * Deletes a collection from the database.
     *
     * @param collection The collection to delete.
     */
    @Delete
    suspend fun delete(collection: Collection)

    /**
     * Upsert a collection and its associated items.
     *
     * @param collection The collection to upsert.
     * @param items The items associated with the collection.
     */
    @Upsert
    suspend fun upsertItemsOfCollection(collection: Collection, items: List<Item>)

    /**
     * Gets all collections associated with an event, along with their items.
     *
     * @param eventId The ID of the event.
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    @Transaction
    @Query("SELECT * FROM collections where event = :eventId order by name ASC")
    fun getEventCollectionsWithItems(eventId: Int): Flow<List<ItemsOfCollection>>

    /**
     * Gets a collection and its associated items by the collection's ID.
     *
     * @param id The ID of the collection.
     * @return An ItemsOfCollection object.
     */
    @Transaction
    @Query("SELECT * from collections where id = :id")
    suspend fun getItemsOfCollection(id: Int): ItemsOfCollection

    /**
     * Gets all collections that are not associated with an event, along with their items.
     *
     * @return A Flow of a list of ItemsOfCollection objects.
     */
    @Transaction
    @Query("SELECT * FROM collections where event is null order by name ASC")
    fun getAllNoEventCollectionsWithItems(): Flow<List<ItemsOfCollection>>

    /**
     * Gets the ID of a collection by its row ID.
     *
     * @param rowId The row ID of the collection.
     * @return The ID of the collection.
     */
    @Query("Select id from collections where rowid = :rowId")
    suspend fun getCollectionId(rowId: Long): Int

    /**
     * Gets the event ID associated with a collection.
     *
     * @param id The ID of the collection.
     * @return The ID of the event, or null if the collection is not associated with an event.
     */
    @Query("Select event from collections where id = :id")
    suspend fun getCollectionEvent(id: Int): Int?

}
