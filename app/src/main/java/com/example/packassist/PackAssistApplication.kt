package com.example.packassist

import android.app.Application
import com.example.packassist.data.AppContainer
import com.example.packassist.data.AppDataContainer

class PackAssistApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this.applicationContext)
    }
}