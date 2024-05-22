package com.example.packassist.data.entitiesAndDaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlin.collections.Collection

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events where id = :id")
    fun getEvent(id: Int): kotlinx.coroutines.flow.Flow<Event>

    @Transaction
    @Query("SELECT * FROM events order by date, name ASC")
    fun getAllEventsWithCollections(): Flow<EventsCollections>

    @Transaction
    @Query("SELECT * FROM events where id = :id")
    fun getEventsCollections(id : Int): Flow<EventsCollections>

}