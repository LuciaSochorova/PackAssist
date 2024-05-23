package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    suspend fun updateCollection(collection: Collection)

    suspend fun insertCollection(collection: Collection)

    suspend fun deleteCollection(collection: Collection)

    suspend fun upsertItemsOfCollection(collection: Collection, items : List<Item>)

    fun getCollectionStream(id : Int): Flow<Collection>

    fun getCollectionsWithItemsStream(): Flow<ItemsOfCollection>

    fun getItemsOfCollectionStream(id : Int):Flow<ItemsOfCollection>
}