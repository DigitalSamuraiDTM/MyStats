package com.mystats.mystats.my_statistics

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.mystats.mystats.MainActivity.MainActivity
import com.mystats.mystats.R


class MyStatistics : Fragment() {


    override fun onAttach(context: Context) {
        (activity as MainActivity).EnableBars(true);
        //findNavController().popBackStack()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fireStore = FirebaseFirestore.getInstance()
        var m : MutableMap<String, String> = HashMap()
        m.put("putin","molodec")

//        fireStore.collection("Users").document().get().addOnSuccessListener(object :  OnSuccessListener<in DocumentSnapshot> {
//            override fun onSuccess(p0: DocumentSnapshot?) {
//
//            }
//
//        })
//            fireStore.collection("Users").get().addOnSuccessListener { snap->
//                Log.d("FIRESTORE", snap.documents.get(1).toString())
//            }
        Log.d("FIRESTORE", FirebaseAuth.getInstance().currentUser.uid.toString());
        fireStore.collection("Users").document(FirebaseAuth.getInstance().currentUser.uid.toString()).get().addOnSuccessListener{ document ->
            Log.d("FIRESTORE", document.data.toString());
            Log.d("FIRESTORE",document.data?.get("PenisSize").toString())
            Toast.makeText(activity?.baseContext,"СЧИТАЛОСЬ", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{ exception ->

            Log.d("FIRESTORE", "ERROR");
            Toast.makeText(activity?.baseContext,"НЕ ПОШЛО", Toast.LENGTH_LONG).show()
        }

//        fireStore.collection("obama").document("monkey").set(m).addOnCompleteListener(object :  OnCompleteListener<Void> {
//            override fun onComplete(p0: Task<Void>) {
//                if (p0.isSuccessful){
//                    Log.d("FIRESTORE","NIIICE")
//                } else{
//                    Log.d("FIRESTORE","BAAAD")
//
//                }
//            }
//
//        })

        super.onViewCreated(view, savedInstanceState)
    }
}