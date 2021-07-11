package com.mystats.mystats.new_statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.mystats.mystats.R
import com.mystats.mystats.my_statistics.InterfaceWithCreatingNewStats


class FragmentTemplatesStats : Fragment() {

    private lateinit var buttonCustomTemplate : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_templates_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PresenterNewStats.getInstance()?.setMyStatsInterface(arguments?.getSerializable("MS") as InterfaceWithCreatingNewStats)
        buttonCustomTemplate = view.findViewById(R.id.fr_templatesStats_button_customStats)
        buttonCustomTemplate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                findNavController().navigate(R.id.action_fragmentTemplatesStats_to_fragmentNameStats)
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }
}