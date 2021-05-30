package com.mystats.mystats.my_statistics

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlin.coroutines.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.AdapterRecord
import com.mystats.mystats.DataStats
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//TODO красота отображение recyclerView.
// ! не работают режимы анфокуса. Сортировка записей по дате создания
class PresenterMyStatistics {
    private lateinit var view: FragmentMyStatistics
    private  var columns : ArrayList<RowStat>? = null
    private lateinit var preferences : SharedPreferences
    private  var sizeStat : Int = 0
    private  var nameStat : String? = null
    private  var recyclerData =  ArrayList<ArrayList<RowStat>>()
    private lateinit var recyclerAdapter : AdapterRecord;

    constructor(view : FragmentMyStatistics){
        this.view = view
        preferences = view.requireActivity().getSharedPreferences("MyStats", Context.MODE_PRIVATE)
        recyclerAdapter = AdapterRecord(recyclerData,false)
    }

    public fun setRootInAdapter(root : ViewGroup){
        recyclerAdapter.setRoot(root)
    }

    public fun setDataInAdapter(adapter : AdapterRecord){
        adapter.setArrayData(recyclerData)
    }
    public  fun getDataFromStats(name : String?, clearColumns : Boolean) = GlobalScope.launch(Dispatchers.Unconfined) {
            //todo использую диспетчер, который работает вместе с главным потоком. Правильно ли это?
        if (name!=null){
            nameStat = name
        }
        view.showLoading()
        //recyclerAdapter.notifyItemRangeRemoved(0,recyclerData.size)
        recyclerData = ArrayList<ArrayList<RowStat>>()
        if (clearColumns){
                columns = null
            }
            if (columns == null || (columns != null && columns?.isEmpty() == true)) {
                columns = getColumnsFromStats(nameStat!!);
            }
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(nameStat!!).collection("DATA").get()
                .addOnSuccessListener { snap ->

                    for (i: Int in 0..snap.size() - 1) {
                        //выполняем глубокое копирование, чтобы в columns не хранились данные
                        val noteData  = (columns!!.map { it.clone() }) as ArrayList<RowStat>

                        //сохраняем данные в записи и запись добавляем в общий массив записей
                        for (j: Int in 0..noteData.size - 1) {
                            noteData[j].setData(snap.documents[i].get(noteData[j].getNameRow()))
                        }
                        recyclerData.add(noteData)
                    }
                    view.clearRecycler()
                    recyclerAdapter.setNewData(recyclerData)
                    if (recyclerData.size ==0){
                        view.showEmptyStats()
                    } else{
                        recyclerAdapter.notifyDataSetChanged()
                        view.showDataLayout()
                    }
                }.addOnFailureListener {
                    //todo обработка ошибок
                }
    }


     suspend  fun getColumnsFromStats(name : String) : ArrayList<RowStat>? {
         return suspendCoroutine { continuation ->
             FirebaseFirestore.getInstance().collection("Users")
                 .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                 .collection("STATS").document(name)
                 .collection("COLUMNS").document("COLUMNS").get()
                 .addOnSuccessListener { doc ->
                     val names = doc.get("NAMES") as List<String>
                     val types = doc.get("TYPES") as List<Int>
                     val columns = ArrayList<RowStat>()
                     for (i: Int in 0..types.size - 1) {

                         when (types[i]) {
                             RowStat.TYPE_STRING -> {
                                 val col = StringRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_INT -> {
                                 val col = NumberRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_DATE -> {
                                 val col = DateRowStat(names[i], null)
                                 columns.add(col)
                             }
                             RowStat.TYPE_STATE -> {
                                 val col = StateRowStat(names[i], null)
                                 columns.add(col)
                             }
                         }
                     }
                    continuation.resume(columns)
                 }.addOnFailureListener {
                     Log.d("FIRESTORE", "ops")
                 }
         }
     }
    fun saveLastStat(name : String?){
        preferences.edit().putString("LastStatName",name).apply()
    }
    fun loadLastStat() : String?{
        nameStat = preferences.getString("LastStatName", null);
        return nameStat
    }

    fun getNamesStats(){
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").get().addOnSuccessListener { snap ->

                    val data = ArrayList<String>()
                    for(i : Int in 0..snap.size()-1){
                        data.add(snap.documents[i].id)
                    }
                    view.addNamesStatsInSubMenu(data)
                }
    }

    public fun goToNewRecord(){
        val bundle = Bundle()
        //columns!!.map { it.clone() }) as ArrayList<RowStat>
        bundle.putSerializable("COLUMNS", (columns!!.map { it.clone() } as ArrayList<RowStat>))
        bundle.putString("NAMESTAT",nameStat)
        //todo нужно ли нам будет знать о том, какое имя у документа с записью?
        sizeStat = 3
        bundle.putInt("SIZESTAT",sizeStat)
        view.findNavController().navigate(R.id.action_myStatistics_to_fragmentNewRecord,bundle)
    }


    public fun addRecordInRecycler(data : ArrayList<RowStat>){
        this.recyclerData.add(data)
    }

    //Todo заглушка! Вместо него необходимо юзать ViewState у Moxy
    public fun initViewState(){
        if (DataStats.data.size!=0){
            recyclerData.addAll(DataStats.data)
            nameStat = DataStats.nameStat
            sizeStat = DataStats.sizeStat
            columns = DataStats.columns
        }
    }

    public fun saveViewState(){
        DataStats.data.clear()
        DataStats.data = recyclerData
        DataStats.nameStat = nameStat
        DataStats.sizeStat = sizeStat
        DataStats.columns = columns
    }

    fun getRecyclerAdapter(): AdapterRecord {
        return recyclerAdapter
    }

    fun newStatsWasCreated(nameStat: String?, columns: ArrayList<RowStat>) {
        recyclerData.clear()
        this.nameStat = nameStat
        this.columns = columns
        saveLastStat(nameStat)
        view.showEmptyStats()
        view.changeTitleName(nameStat)

    }

    fun appWasStarted() {
        nameStat = loadLastStat()
        if (nameStat==null){
            view.showNewStats()
        } else{
            view.changeTitleName(nameStat)
            this.getDataFromStats(null, false)
        }
    }

}