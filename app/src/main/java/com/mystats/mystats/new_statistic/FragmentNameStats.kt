package com.mystats.mystats.new_statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.mystats.mystats.R


class FragmentNameStats : Fragment() {

    private lateinit var editNameStats : EditText
    private lateinit var buttonGoNext : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editNameStats = view.findViewById(R.id.fr_nameStats_edit_nameStats)
        buttonGoNext = view.findViewById(R.id.fr_nameStats_button_goNext)
        buttonGoNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                //todo SAVE name stats
                findNavController().navigate(R.id.action_fragmentNameStats_to_fragmentStatsColumns)
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }
}