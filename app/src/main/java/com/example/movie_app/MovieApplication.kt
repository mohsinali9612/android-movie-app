package com.example.movie_app

import android.app.Application
import android.content.Context
import com.example.movie_app.di.initKoin


class MovieApplication : Application() {

    companion object {
        lateinit var appInstance: MovieApplication
        fun getApplication(): MovieApplication = appInstance
        fun getContext(): Context = appInstance.applicationContext
    }


    override fun onCreate() {
        appInstance = this
        super.onCreate()
        initKoin()
    }

}