package com.example.packassist

import android.app.Application
import com.example.packassist.data.AppContainer
import com.example.packassist.data.AppDataContainer


/**
 * Pack assist application
 *
 * @constructor Create empty Pack assist application
 */
class PackAssistApplication : Application() {
    /**
     * The application container.
     */
    lateinit var container: AppContainer

    /**
     * Called when the application is created.
     *
     * Initialize the application container.
     */
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this.applicationContext)
    }
}