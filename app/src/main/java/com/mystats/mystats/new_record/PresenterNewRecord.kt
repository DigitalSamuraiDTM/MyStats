package com.mystats.mystats.new_record

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.R
import com.mystats.mystats.my_statistics.InterfaceWithNewRecord
import com.mystats.mystats.rowsData.RowStat

class PresenterNewRecord {
    private var view: FragmentNewRecord

    constructor(view : FragmentNewRecord, ){
        this.view = view;
    }

    public fun createNewRecord(
        data: ArrayList<RowStat>,
        nameStat: String,
        address: Int,
        callMyStats: InterfaceWithNewRecord?
    ){
        view.showLoading()

        var out = HashMap<String,Any>()
        for(i : Int in 0..data.size-1){
            out.put(data[i].getNameRow().toString(), data[i].getData()!!)
        }
        out.put("FIRESTORE_DATESTAMP_CREATE",com.google.firebase.firestore.FieldValue.serverTimestamp())
        FirebaseFirestore.getInstance().collection("Users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .collection("STATS").document(nameStat)
            .collection("DATA").document().set(out)
            .addOnSuccessListener {
                val bundle = Bundle()
                bundle.putSerializable("NOTE", data)
                callMyStats?.addNewRecord()
                view.findNavController().navigate(R.id.action_fragmentNewRecord_to_myStatistics, bundle)
            }.addOnFailureListener{
                view.showError()
            }
    }
}
//TODO колонки не должны повторяться