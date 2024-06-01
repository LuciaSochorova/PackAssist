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
    suspend fun insert(event: Event) : Long

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

    @Query("Select id from events where rowid = :rowId")
    suspend fun getEventId(rowId : Long) : Int
    @Query("SELECT * FROM events where id = :id")
    fun getEvent(id: Int): Flow<Event>

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events order by date, name ASC")
    fun getAllEventsOrdered(): Flow<List<Event>>

    @Transaction
    @Query("SELECT * FROM events where id = :id")
    suspend fun getEventsCollections(id: Int): EventsCollections

    @Query ("SELECT name FROM events where id = :id")
    suspend fun getEventName(id: Int) : String

}