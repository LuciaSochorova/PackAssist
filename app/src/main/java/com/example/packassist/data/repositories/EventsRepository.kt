package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventsCollections
import kotlinx.coroutines.flow.Flow

interface EventsRepository {
    suspend fun insertEvent(event: Event) : Long

    suspend fun updateEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun deleteEventAndCollections(event: Event, collections: List<Collection>)

    suspend fun upsertEventsCollections(
        event: Event,
        collections: List<Collection>
    )

    suspend fun getEventId(rowId : Long) : Int
    fun getAllEventsStream(): Flow<List<Event>>
    fun getAllEventsOrderedStream(): Flow<List<Event>>

    fun getEventStream(id: Int): Flow<Event>
    suspend fun getEventName(id: Int) : String

    suspend fun getEventCollections(id: Int): EventsCollections

}