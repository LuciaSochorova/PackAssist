package com.example.packassist.data.repositories

import com.example.packassist.data.entitiesAndDaos.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    suspend fun updateItem(item : Item)

    suspend fun deleteItem(item : Item)

    suspend fun upsertItem(item : Item)

    fun getItemStream(id : Int) : Flow<Item>
}