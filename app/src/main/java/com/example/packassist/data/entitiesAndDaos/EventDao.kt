package com.example.packassist.data.entitiesAndDaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object for the Event entity.
 *
 */
@Dao
interface EventDao {
    /**
     * Inserts a new event into the database.
     *
     * @param event The event to insert.
     * @return The row ID of the newly inserted event.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    /**
     * Updates an existing event in the database.
     *
     * @param event The event to update.
     */
    @Update
    suspend fun update(event: Event)

    /**
     * Deletes an event from the database.
     *
     * @param event The event to delete.
     */
    @Delete
    suspend fun delete(event: Event)

    /**
     * Gets the ID of an event by its row ID.
     *
     * @param rowId The row ID of the event.
     * @return The ID of the event.
     */
    @Query("Select id from events where rowid = :rowId")
    suspend fun getEventId(rowId: Long): Int

    /**
     * Gets all events.
     *
     * @return A Flow of a list of events.
     */
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    /**
     * Gets all events ordered by date and name.
     *
     * @return A Flow of a list of events.
     */
    @Query("SELECT * FROM events order by date, name ASC")
    fun getAllEventsOrdered(): Flow<List<Event>>

    /**
     * Gets an event and its associated collections by the event's ID.
     *
     * @param id The ID of the event.
     * @return An EventsCollections object.
     */
    @Transaction
    @Query("SELECT * FROM events where id = :id")
    suspend fun getEventsCollections(id: Int): EventsCollections

    /**
     * Gets the name of an event by its ID.
     *
     * @param id The ID of the event.
     * @return The name of the event.
     */
    @Query("SELECT name FROM events where id = :id")
    suspend fun getEventName(id: Int): String

}