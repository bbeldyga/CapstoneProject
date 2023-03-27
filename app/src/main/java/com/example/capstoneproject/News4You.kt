package com.example.capstoneproject

import android.app.Application
import com.example.capstoneproject.dataobjects.AppContainer

class News4You: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}