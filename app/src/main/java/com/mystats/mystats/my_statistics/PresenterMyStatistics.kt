package com.mystats.mystats.my_statistics

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.coroutines.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.rowsData.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class PresenterMyStatistics {
    private lateinit var view: FragmentMyStatistics
    private  var columns : ArrayList<RowStat>? = null
    private lateinit var preferences : SharedPreferences
    constructor(view : FragmentMyStatistics){
        this.view = view
        preferences = view.requireActivity().getSharedPreferences("MyStats", Context.MODE_PRIVATE)
    }
    public  fun getDataFromStats(name : String, clearColumns : Boolean) = GlobalScope.launch(Dispatchers.Unconfined) {
            //todo использую диспетчер, который работает вместе с главным потоком. Правильно ли это?
            view.showLoading()
            if (clearColumns){
                columns = null
            }
            if (columns == null || (columns != null && columns?.isEmpty() == true)) {
                columns = getColumnsFromStats(name);
            }

            FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(name).collection("DATA").get()
                .addOnSuccessListener { snap ->
                    var outData = ArrayList<ArrayList<RowStat>>()
                    var noteData = columns
                    //Log.d("FIRESTORE", snap.documents.toString())
                    for (i: Int in 0..snap.size() - 1) {
                        for (j: Int in 0..noteData!!.size - 1) {
                            noteData[j].setData(snap.documents[i].get(noteData[j].getNameRow()))
                        }
                        outData.add(noteData)
                        noteData = columns
                    }

                    view.showDataStats(outData)
                }.addOnFailureListener {

                }
    }


     suspend  fun getColumnsFromStats(name : String) : ArrayList<RowStat>? {
         return suspendCoroutine { continuation ->
             FirebaseFirestore.getInstance().collection("Users")
                 .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                 .collection("STATS").document(name)
                 .collection("COLUMNS").document("COLUMNS").get()
                 .addOnSuccessListener { doc ->
                     Log.d("FIRESTORE", doc.get("NAMES").toString())
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
        return preferences.getString("LastStatName", null);
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
}