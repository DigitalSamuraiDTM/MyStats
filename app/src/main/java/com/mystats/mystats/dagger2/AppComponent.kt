package com.mystats.mystats.dagger2

import com.mystats.mystats.my_statistics.FragmentMyStatistics
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MyStatisticsModule::class])
@Singleton
interface AppComponent {
    fun inject(FragmentMyStats : FragmentMyStatistics);
}