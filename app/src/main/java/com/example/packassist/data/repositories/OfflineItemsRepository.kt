package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemDao
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val itemDao : ItemDao) : ItemsRepository {
    override suspend fun updateItem(item: Item) = itemDao.update(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun upsertItem(item: Item) = itemDao.upsert(item)

    override fun getItemStream(id : Int) : Flow<Item> = itemDao.getItem(id)
}