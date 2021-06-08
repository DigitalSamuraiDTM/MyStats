package com.mystats.mystats.my_statistics

import android.os.Bundle
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState
import moxy.viewstate.strategy.alias.Skip

interface MvpViewMyStatistics : MvpView {
    @SingleState
    fun showLoading();
    @SingleState
    fun showDataLayout();
    @SingleState
    fun showNewStats();
    @SingleState
    fun showEmptyStats();
    @Skip
    fun changeTitleName(nameStat : String?);
    @Skip
    fun navigateToNewRecord(bundle : Bundle);
    @Skip
    fun clearRecycler();
    @Skip
    fun addNamesStatsInSubMenu(data : ArrayList<String>);
}