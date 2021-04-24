package com.mystats.mystats.new_statistic

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.mystats.mystats.R


class FragmentNameStats : Fragment() {

    private lateinit var editNameStats : EditText
    private lateinit var buttonGoNext : Button
    private lateinit var layoutData : ConstraintLayout
    private lateinit var layoutLoading : ConstraintLayout
    private lateinit var viewErrors : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewErrors = view.findViewById(R.id.fr_nameStats_view_errors)
        layoutData = view.findViewById(R.id.fr_nameStats_layout_data)
        layoutLoading = view.findViewById(R.id.fr_nameStats_layout_loading)

        editNameStats = view.findViewById(R.id.fr_nameStats_edit_nameStats)
        buttonGoNext = view.findViewById(R.id.fr_nameStats_button_goNext)
        buttonGoNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                PresenterNewStats.getInstance()?.checkNameMatch(editNameStats.text.toString(),this@FragmentNameStats)
                //findNavController().navigate(R.id.action_fragmentNameStats_to_fragmentStatsColumns)
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }
    fun showLoading(){
        layoutData.visibility = View.GONE
        layoutLoading.visibility = View.VISIBLE
    }
    fun hideLoading(){
        layoutData.visibility = View.VISIBLE
        layoutLoading.visibility = View.GONE
    }
    @SuppressLint("SetTextI18n")
    fun showError(error : Int){
        //1 - empty
        //2 - name is exists
        //3 - error of response
        //0 - clear
        when(error){
            0 ->{
                viewErrors.setText("")
                viewErrors.visibility = View.GONE
            }
            1 ->{
                viewErrors.setText("Name is empty :(")
                viewErrors.visibility = View.VISIBLE
            }
            2 ->{
                viewErrors.setText("Oops...\n This name has already been used")
                viewErrors.visibility = View.VISIBLE
            }
            3 ->{
                viewErrors.setText("We can't think of your name\n" +
                        "Maybe there are problems with the internet?")
                viewErrors.visibility = View.VISIBLE
            }
        }
    }
    fun goToColumns(){
        findNavController().navigate(R.id.action_fragmentNameStats_to_fragmentStatsColumns)
    }
}