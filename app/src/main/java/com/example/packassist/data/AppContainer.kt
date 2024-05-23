package com.example.packassist.data

import android.content.Context
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.EventsRepository
import com.example.packassist.data.repositories.ItemsRepository
import com.example.packassist.data.repositories.OfflineCollectionRepository
import com.example.packassist.data.repositories.OfflineEventsRepository
import com.example.packassist.data.repositories.OfflineItemsRepository

interface AppContainer {
    val itemsRepository: ItemsRepository
    val collectionsRepository : CollectionsRepository
    val eventsRepository : EventsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(AppDatabase.getDatabase(context).itemDao())
    }
    override val collectionsRepository: CollectionsRepository by lazy {
        OfflineCollectionRepository(AppDatabase.getDatabase(context).collectionDao())
    }
    override val eventsRepository: EventsRepository by lazy {
        OfflineEventsRepository(AppDatabase.getDatabase(context).eventDao())
    }
}