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


@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Delete
    suspend fun deleteEventAndCollections(event: Event, collections: List<Collection>)

    @Upsert
    suspend fun upsertEventsCollections(
        event: Event,
        collections: List<Collection>
    )

    @Query("SELECT * FROM events where id = :id")
    fun getEvent(id: Int): Flow<Event>

    @Transaction
    @Query("SELECT * FROM events order by date, name ASC")
    fun getAllEventsWithCollections(): Flow<EventsCollections>

    @Transaction
    @Query("SELECT * FROM events where id = :id")
    fun getEventsCollections(id: Int): Flow<EventsCollections>

}