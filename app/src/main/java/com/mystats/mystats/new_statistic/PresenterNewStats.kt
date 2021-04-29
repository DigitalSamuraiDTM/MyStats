package com.mystats.mystats.new_statistic

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.rowsData.RowStat

class PresenterNewStats {
    private lateinit var NameStats : String
    val userStore = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString())
    companion object {
        private  var presenter : PresenterNewStats? = null

        public fun getInstance(): PresenterNewStats? {
            if (presenter == null) {
                presenter = PresenterNewStats()
            }
            return presenter
        }
    }

    fun checkNameMatch(name : String, view : FragmentNameStats){
        if(name.isEmpty() ){
            view.showError(1)
            return
        }
        view.showError(0)
        view.showLoading()
        //todo как-то проверять на наличие интернета
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").whereEqualTo("NameStats",name).get().addOnSuccessListener { snap ->
                    if (snap.documents.size==0){
                        NameStats = name
                        view.goToColumns()
                        view.showError(0)
                        view.hideLoading()
                    } else{
                        view.hideLoading()
                        view.showError(2)
                    }
                }.addOnFailureListener{ exception ->
                        view.hideLoading()
                        view.showError(3)
                }
    }
    fun createNewStats(columns: ArrayList<RowStat>, view: FragmentStatsColumns){
        if (columns.isEmpty()){
            view.showError(0)
            return
        }
        view.showLoading()
        var columnsData = HashMap<String, ArrayList<String>>()
        val nameColumns = ArrayList<String>()
        val typeColumns = ArrayList<String>()
        for (i : Int in 0..columns.size-1){
            nameColumns.add(columns[i].getNameRow())
            typeColumns.add(columns[i].getNameType())
        }
        columnsData.set("NAMES", nameColumns)
        columnsData.set("TYPES", typeColumns)
        userStore.collection("STATS").document(NameStats)
                .collection("COLUMNS").document("COLUMNS").set(columnsData)
                .addOnSuccessListener {
                    setSettingsStats(view)
                }.addOnFailureListener{
                    view.hideLoading()
                    view.showError(1)
                }
    }
    private fun setSettingsStats(view : FragmentStatsColumns){
        val data = hashMapOf("STATNAME" to NameStats)
        userStore.collection("STATS").document(NameStats).set(data).addOnSuccessListener {
            view.finish()
        }.addOnFailureListener{
            view.hideLoading()
            view.showError(2)
        }
    }
}