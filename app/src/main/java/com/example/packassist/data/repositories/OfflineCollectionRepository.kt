package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.CollectionDao
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemsOfCollection
import kotlinx.coroutines.flow.Flow

class OfflineCollectionRepository(private val collDao : CollectionDao) : CollectionsRepository{
    override suspend fun updateCollection(collection: Collection) {
        collDao.update(collection)
    }

    override suspend fun insertCollection(collection: Collection) : Long = collDao.insert(collection)


    override suspend fun deleteCollection(collection: Collection) {
        collDao.delete(collection)
    }


    override suspend fun upsertItemsOfCollection(collection: Collection, items: List<Item>) {
        collDao.upsertItemsOfCollection(collection, items)
    }

    override fun getCollectionStream(id: Int): Flow<Collection> = collDao.getCollection(id)


    override fun getEventCollectionsWithItemsStream(eventId : Int): Flow<List<ItemsOfCollection>> = collDao.getEventCollectionsWithItems(eventId)


    override suspend fun getItemsOfCollection(id: Int): ItemsOfCollection = collDao.getItemsOfCollection(id)
    override fun getAllNoEventCollectionsWithItems(): Flow<List<ItemsOfCollection>> = collDao.getAllNoEventCollectionsWithItems()
    override suspend fun getCollectionId(rowId: Long): Int = collDao.getCollectionId(rowId)
    override suspend fun getCollectionEvent(id : Int) : Int? = collDao.getCollectionEvent(id)
}