package com.mystats.mystats.my_statistics

import android.util.Log
import kotlinx.coroutines.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.rowsData.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class PresenterMyStatistics {
    private lateinit var view: FragmentMyStatistics
    private  var columns : ArrayList<RowStat>? = null
    constructor(view : FragmentMyStatistics){
        this.view = view
    }
    public  fun getDataFromStats(name : String) {
        GlobalScope.launch {

            view.showLoading()
            getColumnsFromStats(name)
            if (columns == null || (columns != null && columns?.isEmpty() == true)) {
                columns = getColumnsFromStats(name);
            }

            FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(name).collection("DATA").get()
                .addOnSuccessListener { snap ->
                    var outData = ArrayList<ArrayList<RowStat>>()
                    var noteData = columns
                    for (i: Int in 0..snap.size() - 1) {
                        for (j: Int in 0..noteData!!.size - 1) {
                            noteData[j].setData(snap.documents[i].get(noteData[i].getNameType()))
                        }
                        outData.add(noteData)
                        noteData = columns
                    }
                    view.showDataStats(outData)
                }.addOnFailureListener {

                }
        }
    }


     suspend  fun getColumnsFromStats(name : String) : ArrayList<RowStat>? {
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(name)
                .collection("COLUMNS").document("COLUMNS").get()
                .addOnSuccessListener { doc ->
                    Log.d("FIRESTORE", doc.get("NAMES").toString())
                    val names = doc.get("NAMES") as List<String>
                    val i = 0
                    val types = doc.get("TYPES") as List<Int>
                    val columns = ArrayList<RowStat>()
                    for (i : Int in 0..types.size-1){
                        
                        when(types[i]){
                            RowStat.TYPE_STRING ->{
                                val col = StringRowStat(names[i], null)
                                columns.add(col)
                            }
                            RowStat.TYPE_INT ->{
                                val col = NumberRowStat(names[i], null)
                                columns.add(col)
                            }
                            RowStat.TYPE_DATE ->{
                                val col = DateRowStat(names[i], null)
                                columns.add(col)
                            }
                            RowStat.TYPE_STATE ->{
                                val col = StateRowStat(names[i], null)
                                columns.add(col)
                            }
                        }

                    }

                }.addOnFailureListener{
                    Log.d("FIRESTORE", "ops")
                }
        //return columns!!
    }
}