package com.mystats.mystats.dialogs_fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mystats.mystats.MainApplication
import com.mystats.mystats.R
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogDeleteStats(private var nameStat: String,
private var settingsView : InterfaceForDialogDeleteStats) : DialogFragment(), CoroutineScope {
    private lateinit var editNameStats : EditText
    private lateinit var textViewDeleteStats : TextView

    init {

        setStyle(STYLE_NO_TITLE, R.style.dialogDelete)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it,R.style.dialogDelete)

            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_delete_stats,null)

            textViewDeleteStats = view.findViewById(R.id.dialog_deleteStats_view_stats);
            textViewDeleteStats.setText("Enter "+nameStat+" to remove statistics")
            editNameStats = view.findViewById(R.id.dialog_deleteStats_edit_nameStats);
            editNameStats.setHint(nameStat);
            builder.setView(view)
                .setPositiveButton("Delete", DialogInterface.OnClickListener{ dialogInterface, i ->
                    if (nameStat==editNameStats.text.toString()){

                        launch {
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                            (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).isEnabled = false
                            deleteColumnsInStats()
                            deleteNotesInStats()
                            deleteStats()
                        }

                    } else{
                        Toast.makeText(MainApplication.getContext(),"Enter a secret key",Toast.LENGTH_SHORT).show()

                    }
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener(){ dialogInterface, i ->
                    dialog?.cancel()
                })

            builder.create()
        } ?: throw IllegalStateException("act cannot be null")
    }
    suspend fun deleteColumnsInStats() : Boolean {
        return suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(nameStat).collection("COLUMNS")
                .document("COLUMNS").delete().addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resume(false)
                }
        }
    }
    suspend fun deleteNotesInStats() : Boolean  {
        return suspendCoroutine {continuation->
            val s = settingsView.getDocumentsId()
            s.forEach {
                FirebaseFirestore.getInstance().collection("Users")
                    .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                    .collection("STATS").document(nameStat).collection("DATA")
                    .document(it).delete().addOnSuccessListener {

                    }.addOnFailureListener {

                    }
            }
            continuation.resume(true)
        }
    }
    suspend fun deleteStats() : Boolean {
        return suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .collection("STATS").document(nameStat).delete().addOnSuccessListener {
                    continuation.resume(true)
                }.addOnFailureListener {
                    continuation.resume(false)
                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}