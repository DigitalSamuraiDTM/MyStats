package com.mystats.mystats

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MainApplication : Application() {
    override fun onCreate() {
        context = applicationContext;
        super.onCreate()
    }
    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var context : Context
        fun getContext() : Context{
            return context
        }
    }
}