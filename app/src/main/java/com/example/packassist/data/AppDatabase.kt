package com.example.packassist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.CollectionDao
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventDao
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemDao

@Database(
    entities = [
        Collection::class, Event::class, Item::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun eventDao(): EventDao
    abstract fun itemDao(): ItemDao


    companion object {
        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}