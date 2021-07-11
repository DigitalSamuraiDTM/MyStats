package com.mystats.mystats.new_record

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.R
import com.mystats.mystats.my_statistics.InterfaceWithNewRecord
import com.mystats.mystats.rowsData.NoteStats
import com.mystats.mystats.rowsData.RowStat

class PresenterNewRecord {
    private var view: FragmentNewRecord
    constructor(view : FragmentNewRecord, ){
        this.view = view;
    }

    public fun createNewRecord(
        data: NoteStats,
        nameStat: String,
        callMyStats: InterfaceWithNewRecord?
    ){
        view.showLoading()

        var out = HashMap<String,Any>()
        for(i : Int in 0..data.data.size-1){
            out.put(data.data[i].getNameRow().toString(), data.data[i].getData()!!)
        }
        //записываем айдишник в новую запись
        val doc = FirebaseFirestore.getInstance().collection("Users")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString()).collection("STATS")
            .document(nameStat).collection("DATA").document()
        data.noteId = doc.id
        out.put("FIRESTORE_DATESTAMP_CREATE",com.google.firebase.firestore.FieldValue.serverTimestamp())
        doc.set(out)
            .addOnSuccessListener {

                callMyStats?.addNewRecord()
                view.findNavController().navigate(R.id.action_fragmentNewRecord_to_myStatistics,
                Bundle().also{
                    it.putSerializable("NOTE", data)
                })
            }.addOnFailureListener{
                view.showError()
            }
    }
}
//TODO колонки не должны повторяться