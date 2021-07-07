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
import java.lang.IllegalStateException

class DialogDeleteStats(private var nameStat: String,
private var finish : InterfaceForDialogDeleteStats) : DialogFragment() {
    private lateinit var editNameStats : EditText
    private lateinit var textViewDeleteStats : TextView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_delete_stats,null)

            textViewDeleteStats = view.findViewById(R.id.dialog_deleteStats_view_stats);
            textViewDeleteStats.setText("Enter "+nameStat+" to remove statistics")
            editNameStats = view.findViewById(R.id.dialog_deleteStats_edit_nameStats);
            editNameStats.setHint(nameStat);

            builder.setTitle("Deleting statistics").setView(view)
                .setPositiveButton("Delete", DialogInterface.OnClickListener{ dialogInterface, i ->
                    if (nameStat==editNameStats.text.toString()){

                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE).isEnabled = false
                        FirebaseFirestore.getInstance().collection("Users")
                            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                            .collection("STATS").document(nameStat).delete().addOnSuccessListener {
                                Toast.makeText(MainApplication.getContext(),nameStat+" was burned!", Toast.LENGTH_SHORT).show()
                                // финиш - делаем колл к настройкам, чтобы они захлопнулись
                                dialog?.dismiss()
                                finish.finish()
                            }.addOnFailureListener{
                                Toast.makeText(MainApplication.getContext(),"Oops, something went wrong", Toast.LENGTH_SHORT).show()
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
}