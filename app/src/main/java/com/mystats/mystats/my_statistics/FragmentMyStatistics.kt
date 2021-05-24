package com.mystats.mystats.my_statistics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.mystats.mystats.MainActivity.MainActivity
import com.mystats.mystats.R


class FragmentMyStatistics : Fragment(), View.OnClickListener {

    private lateinit var buttonNewRecord : Button
    private lateinit var buttonSearchInStats : ImageButton
    private lateinit var buttonInfoStats : ImageButton
    private lateinit var buttonNewStats : Button
    private lateinit var layoutLoading : ConstraintLayout
    private lateinit var layoutNewStats : ConstraintLayout
    private lateinit var layoutMainData : ConstraintLayout



    override fun onResume() {
        (activity as MainActivity).EnableBars(true);

        super.onResume()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_myStats_newStats->{
                (activity as MainActivity).EnableBars(false)
                findNavController().navigate(R.id.action_myStatistics_to_fragmentTemplatesStats)
            }
            R.id.item_myStats_settings->{
                Log.d("FIRESTORE","OBEME")

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_stats,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_my_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        buttonNewRecord = view.findViewById(R.id.fr_myStats_button_newRecord)
        buttonNewRecord.setOnClickListener(this);
        buttonSearchInStats = view.findViewById(R.id.fr_myStats_button_searchInStats)
        buttonSearchInStats.setOnClickListener(this)
        buttonInfoStats  = view.findViewById(R.id.fr_myStats_button_infoAboutStats)
        buttonInfoStats.setOnClickListener(this)
        buttonNewStats = view.findViewById(R.id.fr_myStats_button_createStats)
        buttonNewStats.setOnClickListener(this)
        layoutLoading  = view.findViewById(R.id.fr_myStats_layout_loading)
        layoutNewStats = view.findViewById(R.id.fr_myStats_layout_new_stats)
        layoutMainData = view.findViewById(R.id.fr_myStats_layout_mainData)

        super.onViewCreated(view, savedInstanceState)
    }
    public fun showLoading(){
        layoutLoading.visibility = View.VISIBLE
        layoutMainData.visibility = View.VISIBLE
        layoutNewStats.visibility = View.GONE
    }
    public fun showData(){
        layoutLoading.visibility = View.GONE
        layoutMainData.visibility = View.VISIBLE
        layoutNewStats.visibility = View.GONE
    }

    public fun showNewStats(){
        layoutLoading.visibility = View.GONE
        layoutMainData.visibility = View.GONE
        layoutNewStats.visibility = View.VISIBLE
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.fr_myStats_button_createStats ->{
                (activity as MainActivity).EnableBars(false)
                findNavController().navigate(R.id.action_myStatistics_to_fragmentTemplatesStats)
            }

        }
    }
}