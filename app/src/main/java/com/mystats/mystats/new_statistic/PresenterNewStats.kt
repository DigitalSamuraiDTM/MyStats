package com.mystats.mystats.new_statistic

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.rowsData.RowStat

class PresenterNewStats {


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
                .collection("DATA").whereEqualTo("NameStats",name).get().addOnSuccessListener { snap ->
                    if (snap.documents.size==0){
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
    fun createNewStats(columns : ArrayList<RowStat>){

        //todo создание новой базы данных
    }
}