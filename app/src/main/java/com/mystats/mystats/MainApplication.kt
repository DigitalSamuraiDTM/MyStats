package com.mystats.mystats

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.mystats.mystats.dagger2.AppComponent
import com.mystats.mystats.dagger2.DaggerAppComponent
import com.mystats.mystats.dagger2.MyStatisticsModule

class MainApplication : Application() {
    override fun onCreate() {
        context = applicationContext;
        context.setTheme(R.style.MyStats)
        daggerApp = buildDaggers();
        super.onCreate()
    }
    //даггер будет являться синглтоном и жить вместе с application
    fun buildDaggers() : AppComponent{
        return DaggerAppComponent.builder().myStatisticsModule(MyStatisticsModule()).build();
    }

    companion object{
        private lateinit var daggerApp : AppComponent;
        @SuppressLint("StaticFieldLeak")
        private lateinit var context : Context
        fun getContext() : Context{
            return context
        }
        fun getAppComponent() : AppComponent {
            return daggerApp;
        }
    }
}