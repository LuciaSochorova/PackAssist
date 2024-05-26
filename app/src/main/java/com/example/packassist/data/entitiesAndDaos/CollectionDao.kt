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
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    @Update
    suspend fun update(collection : Collection)

    @Delete
    suspend fun delete(collection : Collection)

    @Upsert
    suspend fun upsertItemsOfCollection(collection: Collection, items : List<Item>)

    @Query("SELECT * FROM collections where id = :id")
    fun getCollection(id: Int): Flow<Collection>

    @Transaction
    @Query("SELECT * FROM collections order by name ASC")
    fun getCollectionsWithItems(): Flow<List<ItemsOfCollection>>

    @Transaction
    @Query("SELECT * from collections where id = :id")
    suspend fun getItemsOfCollection(id : Int):ItemsOfCollection

    @Query("SELECT * FROM collections where event = null")
    fun getAllNoEventCollections() : Flow<List<Collection>>

    @Query("Select id from collections where rowid = :rowId")
    fun getCollectionId(rowId : Long) : Int

}
