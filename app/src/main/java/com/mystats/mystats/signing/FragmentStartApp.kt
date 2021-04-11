package com.mystats.mystats.signing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.mystats.mystats.R


class FragmentStartApp() : Fragment(), View.OnClickListener {
    private lateinit var buttonSignUp: Button
    private lateinit var buttonSignIn: Button

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_app, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        buttonSignUp = view.findViewById(R.id.fr_start_sign_up);
        buttonSignUp.setOnClickListener(this)
        buttonSignIn = view.findViewById(R.id.fr_start_sign_in);
        buttonSignIn.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        if (p0?.id==R.id.fr_start_sign_up){
            findNavController().navigate(R.id.action_fragmentStartApp_to_fragmentSignUp)

        } else if(p0?.id==R.id.fr_start_sign_in){
            findNavController().navigate(R.id.action_fragmentStartApp_to_fragmentSignIn)

        }
    }
}