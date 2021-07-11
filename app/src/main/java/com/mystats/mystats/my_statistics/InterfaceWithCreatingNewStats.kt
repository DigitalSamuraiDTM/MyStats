package com.mystats.mystats.my_statistics

import java.io.Serializable
//интерфейс - мост между fragments myStats and TemplatesStats(PresenterNewStats)
interface InterfaceWithCreatingNewStats : Serializable {
    fun newStatsWasCreated();
}