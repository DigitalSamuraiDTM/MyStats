package com.mystats.mystats.dagger2

import androidx.annotation.NonNull
import com.google.firebase.inject.Provider
import com.mystats.mystats.my_statistics.PresenterMyStatistics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//модуль - класс, который содержит провайды
@Module
class MyStatisticsModule {


    //провайд - функция, которая умеет создавать нужный нам объект
    @Provides
    @Singleton
    @NonNull
    fun getPresenterMyStatistics() : PresenterMyStatistics{
        return PresenterMyStatistics()
    }

}