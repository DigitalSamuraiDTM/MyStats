package com.mystats.mystats.dialogs_fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.mystats.mystats.R
import com.mystats.mystats.rowsData.*

class DialogNewRow(private var interfa: InterfaceForDialogNewRow) : DialogFragment() {
    private lateinit var editNameRow : EditText;
    private lateinit var spinnerTypeRow : Spinner;
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_add_new_row, null)


            editNameRow  = view.findViewById(R.id.dialog_newRow_edit_nameRow)
            spinnerTypeRow = view.findViewById(R.id.dialog_newRow_spinner_typeRow)
            val adapter = ArrayAdapter(requireActivity().baseContext,android.R.layout.simple_spinner_item,resources.getStringArray(
                R.array.typesRows
            ))
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerTypeRow.adapter = adapter

            builder.setTitle("New row").setView(view)
                    .setPositiveButton("Add", DialogInterface.OnClickListener{ dialog, id ->
                        if (editNameRow.text.toString().isEmpty()){
                            return@OnClickListener
                        }
                        var data : RowStat? = null

                        when(spinnerTypeRow.selectedItemPosition){
                            0->{ //string
                                data = StringRowStat()
                                data.setNameRow(editNameRow.text.toString())
                            }
                            1->{ //number
                                data = NumberRowStat()
                                data.setNameRow(editNameRow.text.toString())
                            }
                            2->{ //state
                                data = StateRowStat()
                                data.setNameRow(editNameRow.text.toString())
                            }
                            3->{ //date
                                data = DateRowStat()
                                data.setNameRow(editNameRow.text.toString())
                            }
                        }

                        interfa.getData(data)
                        dialog?.dismiss()
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialogInterface, i ->
                        dialog?.cancel()
                    })

            builder.create()
        } ?: throw IllegalStateException("act cannot be null")

    }

}