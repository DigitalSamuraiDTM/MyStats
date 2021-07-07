package com.mystats.mystats.settingsStats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.mystats.mystats.R
import com.mystats.mystats.dialogs_fragments.DialogDeleteStats
import com.mystats.mystats.dialogs_fragments.InterfaceForDialogDeleteStats
import moxy.MvpAppCompatFragment
import moxy.MvpFragment


class FragmentSettingsStats : MvpAppCompatFragment(), View.OnClickListener, InterfaceForDialogDeleteStats {

    private lateinit var buttonDeleteStats : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonDeleteStats = view.findViewById(R.id.fr_settingsStats_button_deleteStats)
        buttonDeleteStats.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.fr_settingsStats_button_deleteStats ->{
                val deleteDialog = DialogDeleteStats(arguments?.getString("NameStat","s")!!, this)
                deleteDialog.show(requireActivity().supportFragmentManager, "DeleteStats")

            }
        }
    }

    override fun finish() {

    }
}