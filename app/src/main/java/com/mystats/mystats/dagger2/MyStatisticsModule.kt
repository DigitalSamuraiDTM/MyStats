package com.mystats.mystats.dagger2

import androidx.annotation.NonNull
import com.google.firebase.inject.Provider
import com.mystats.mystats.my_statistics.PresenterMyStatistics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MyStatisticsModule {

    @Provides
    @Singleton
    @NonNull
    fun getPresenterMyStatistics() : PresenterMyStatistics{
        return PresenterMyStatistics()
    }
//    @Provides
//    fun getPresenterProvider() : PresenterPro{
//
//    }
}