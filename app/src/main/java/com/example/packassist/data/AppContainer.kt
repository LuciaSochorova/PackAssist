package com.example.packassist.data

import android.content.Context
import com.example.packassist.data.repositories.CollectionsRepository
import com.example.packassist.data.repositories.EventsRepository
import com.example.packassist.data.repositories.ItemsRepository
import com.example.packassist.data.repositories.OfflineCollectionRepository
import com.example.packassist.data.repositories.OfflineEventsRepository
import com.example.packassist.data.repositories.OfflineItemsRepository

/**
 * App container interface
 * Provides access to the repositories for items, collections, and events.
 *
 * @constructor Create empty App container
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
    val collectionsRepository : CollectionsRepository
    val eventsRepository : EventsRepository
}

/**
 * App data container
 * Implementation of the AppContainer interface that provides offline repositories.
 *
 * @property context The application context.
 * @constructor Create empty App data container
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * The repository for managing items.
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(AppDatabase.getDatabase(context).itemDao())
    }

    /**
     * The repository for managing collections.
     */
    override val collectionsRepository: CollectionsRepository by lazy {
        OfflineCollectionRepository(AppDatabase.getDatabase(context).collectionDao())
    }

    /**
     * The repository for managing events.
     */
    override val eventsRepository: EventsRepository by lazy {
        OfflineEventsRepository(AppDatabase.getDatabase(context).eventDao())
    }
}