package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventDao
import com.example.packassist.data.entitiesAndDaos.EventsCollections
import kotlinx.coroutines.flow.Flow

class OfflineEventsRepository(private val eventDao: EventDao) : EventsRepository {
    override suspend fun insertEvent(event: Event): Long = eventDao.insert(event)


    override suspend fun updateEvent(event: Event) {
        eventDao.update(event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventDao.delete(event)
    }

    override suspend fun deleteEventAndCollections(
        event: Event,
        collections: List<Collection>
    ) {
        eventDao.deleteEventAndCollections(event, collections)
    }

    override suspend fun upsertEventsCollections(event: Event, collections: List<Collection>) {
        eventDao.upsertEventsCollections(event, collections)
    }
    override suspend fun getEventId(rowId : Long) : Int = eventDao.getEventId(rowId)
    override fun getAllEventsStream(): Flow<List<Event>> = eventDao.getAllEvents()
    override fun getAllEventsOrderedStream(): Flow<List<Event>> = eventDao.getAllEventsOrdered()

    override fun getEventStream(id: Int): Flow<Event> = eventDao.getEvent(id)

    override fun getAllEventsWithCollectionsStream(): Flow<List<EventsCollections>> =
        eventDao.getAllEventsWithCollections()

    override suspend fun getEventCollections(id: Int): EventsCollections =
        eventDao.getEventsCollections(id)
}