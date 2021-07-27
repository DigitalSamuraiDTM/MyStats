package com.mystats.mystats.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.mystats.mystats.R


class FragmentSignUp : Fragment() {
    private  var presenter: PresenterSignUp? = null
    private lateinit var ButtonSignUp : Button
    private lateinit var EditEmail : EditText
    private lateinit var EditPassword : EditText
    private lateinit var EditRepeatPassword : EditText
    private lateinit var LayoutSignUp : ConstraintLayout
    private lateinit var LayoutLoading : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = PresenterSignUp(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LayoutLoading = view.findViewById(R.id.fr_sign_up_layout_loading)
        LayoutSignUp = view.findViewById(R.id.fr_sign_up_layout_sign_up)
        EditEmail = view.findViewById(R.id.fr_sign_up_email)
        EditPassword = view.findViewById(R.id.fr_sign_up_pass)
        EditRepeatPassword = view.findViewById(R.id.fr_sign_up_repeat_pass)
        ButtonSignUp = view.findViewById(R.id.fr_sign_up_button_sign_up)
        ButtonSignUp.setOnClickListener(){
            presenter?.signUpUser(EditEmail.text.toString(),EditPassword.text.toString(), EditRepeatPassword.text.toString())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    public fun showLoading(){
        LayoutSignUp.visibility = View.GONE
        LayoutLoading.visibility = View.VISIBLE
    }
    public fun hideLoading(){
        LayoutSignUp.visibility = View.VISIBLE
        LayoutLoading.visibility = View.GONE
    }
    public fun showError(error : Int){
        when(error){
            ErrorsSigning.ERROR_NOT_EQUAL_PASS -> {
                Toast.makeText(activity?.applicationContext,"Password not equal", Toast.LENGTH_SHORT).show()
            }
            ErrorsSigning.ERROR_REGISTRATION -> {
                Toast.makeText(activity?.applicationContext,"Sign up error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    public fun signInComplete(){
        var bundle : Bundle = Bundle()
        bundle.putString("p", EditPassword.text.toString())
        bundle.putString("m", EditEmail.text.toString())
        findNavController().navigate(R.id.action_fragmentSignUp_to_fragmentSignIn, bundle)
        Toast.makeText(activity?.applicationContext, "We send verification message in your email", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }
}