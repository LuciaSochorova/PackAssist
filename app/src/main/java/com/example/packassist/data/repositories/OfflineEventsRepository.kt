package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventDao
import com.example.packassist.data.entitiesAndDaos.EventsCollections
import kotlinx.coroutines.flow.Flow

/**
 * Offline repository for managing events.
 *
 * @property eventDao The DAO for accessing the event table.
 * @constructor Create empty Offline events repository
 */
class OfflineEventsRepository(private val eventDao: EventDao) : EventsRepository {
    /**
     * Inserts a new event.
     *
     * @param event The event to insert.
     * @return The row ID of the newly inserted event.
     */
    override suspend fun insertEvent(event: Event): Long = eventDao.insert(event)

    /**
     * Update event
     *
     * @param event The event to update.
     */
    override suspend fun updateEvent(event: Event) {
        eventDao.update(event)
    }

    /**
     * Delete event
     *
     * @param event The event to delete.
     */
    override suspend fun deleteEvent(event: Event) {
        eventDao.delete(event)
    }

    /**
     * Gets the ID of an event based on its row ID.
     *
     * @param rowId The row ID of the event.
     * @return
     * The ID of the event.
     */
    override suspend fun getEventId(rowId : Long) : Int = eventDao.getEventId(rowId)

    /**
     * Gets a Flow of a list of all events.
     *
     * @return A Flow of a list of Event objects.
     */
    override fun getAllEventsStream(): Flow<List<Event>> = eventDao.getAllEvents()

    /**
     * Gets a Flow of a list of all events ordered by date and name.
     *
     * @return A Flow of a list of Event objects.
     */
    override fun getAllEventsOrderedStream(): Flow<List<Event>> = eventDao.getAllEventsOrdered()

    /**
     * Gets the name of an event based on its ID.
     *
     * @param id The ID of the event.
     * @return The name of the event.
     */
    override suspend fun getEventName(id: Int) : String = eventDao.getEventName(id)

    /**
     * Gets an EventsCollections object for a given event.
     *
     * @param id The ID of the event.
     * @return An EventsCollections object containing the event and its collections.
     */
    override suspend fun getEventCollections(id: Int): EventsCollections =
        eventDao.getEventsCollections(id)
}