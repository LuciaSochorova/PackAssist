package com.example.packassist.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.packassist.data.entitiesAndDaos.Collection
import com.example.packassist.data.entitiesAndDaos.CollectionDao
import com.example.packassist.data.entitiesAndDaos.Event
import com.example.packassist.data.entitiesAndDaos.EventDao
import com.example.packassist.data.entitiesAndDaos.Item
import com.example.packassist.data.entitiesAndDaos.ItemDao
import java.util.Date

/**
 * Type converters
 *
 * @constructor Create empty Converters
 */
class Converters {
    // copied from https://developer.android.com/training/data-storage/room/referencing-data
    /**
     * Converts a timestamp to a Date object.
     *
     * @param value The timestamp to convert.
     * @return The Date object corresponding to the timestamp.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a Date object to a timestamp.
     *
     * @param date The Date object to convert.
     * @returnThe timestamp corresponding to the Date object.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}


/**
 * The Room database for the application.
 *
 * @constructor Create empty App database
 */
@Database(
    entities = [
        Collection::class, Event::class, Item::class
    ],
    version = 6,
    autoMigrations = [
        AutoMigration (from = 5, to = 6)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for accessing Collection entities.
     *
     * @return The DAO for accessing Collection entities.
     */
    abstract fun collectionDao(): CollectionDao

    /**
     * Returns the DAO for accessing Event entities.
     *
     * @return The DAO for accessing Event entities.
     */
    abstract fun eventDao(): EventDao

    /**
     * Returns the DAO for accessing Item entities.
     *
     * @return The DAO for accessing Item entities.
     */
    abstract fun itemDao(): ItemDao


    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Returns the singleton instance of the database.
         *
         * @param context The application context.
         * @return The singleton instance of the database.
         */
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


