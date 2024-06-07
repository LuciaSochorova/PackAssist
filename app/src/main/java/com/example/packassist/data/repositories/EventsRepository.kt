package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventsCollections
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing events.
 *
 */
interface EventsRepository {
    /**
     * Inserts a new event.
     *
     * @param event The event to insert.
     * @return The row ID of the newly inserted event.
     */
    suspend fun insertEvent(event: Event) : Long

    /**
     * Updates an existing event.
     *
     * @param event  The event to update.
     */
    suspend fun updateEvent(event: Event)

    /**
     * Deletes an event.
     *
     * @param event The event to delete.
     */
    suspend fun deleteEvent(event: Event)

    /**
     * Gets the ID of an event based on its row ID.
     *
     * @param rowId The row ID of the event.
     * @return The ID of the event.
     */
    suspend fun getEventId(rowId : Long) : Int

    /**
     * Gets a Flow of all events.
     *
     * @return A Flow of a list of Event objects.
     */
    fun getAllEventsStream(): Flow<List<Event>>

    /**
     * Gets a Flow of all events ordered.
     *
     * @return A Flow of a list of Event objects.
     */
    fun getAllEventsOrderedStream(): Flow<List<Event>>

    /**
     * Gets the name of an event by its ID.
     *
     * @param id The ID of the event.
     * @return The name of the event.
     */
    suspend fun getEventName(id: Int) : String

    /**
     * Gets the collections associated with an event.
     *
     * @param id The ID of the event.
     * @return An EventsCollections object containing the event and its collections.
     */
    suspend fun getEventCollections(id: Int): EventsCollections

}