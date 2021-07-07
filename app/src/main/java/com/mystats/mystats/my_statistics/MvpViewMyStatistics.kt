package com.mystats.mystats.my_statistics

import android.os.Bundle
import com.mystats.mystats.AdapterRecord
import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState
import moxy.viewstate.strategy.alias.Skip
//интерфейс нашего view c использованием Moxy
//skip - пропускает добавлении команды в viewstate
//SingleState - добавляет команду, перед этим очищая viewstate
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
    @SingleState
    fun updateRecyclerAdapter(adapter : AdapterRecord);
    @Skip
    fun doActionAfterFragment(numAction : Int)
}