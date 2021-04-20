package com.mystats.mystats.sign_in

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
import com.mystats.mystats.ErrorsSigning
import com.mystats.mystats.R


class FragmentSignIn : Fragment() {
    //todo доделать возможность повторной отправки письма верификации на почту
    private var presenter: PresenterSignIn? = null
    private lateinit var editEmail: EditText
    private lateinit var editPass: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var layLoading : ConstraintLayout
    private lateinit var layData : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = PresenterSignIn(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editEmail = view.findViewById(R.id.fr_sign_in_email)
        editPass = view.findViewById(R.id.fr_sign_in_pass)
        layLoading  = view.findViewById(R.id.fr_sign_in_loading)
        layData  = view.findViewById(R.id.fr_sign_in_signing)
        if (arguments?.getString("m") != null && arguments?.getString("p") != null){

            editEmail.setText(arguments?.getString("m"))
            editPass.setText(arguments?.getString("p"))
            //presenter?.sendVerifyMessage()
        }

        buttonSignIn = view.findViewById(R.id.fr_sign_in_button_lets_go)
        buttonSignIn.setOnClickListener( object : View.OnClickListener{
            override fun onClick(p0: View?) {
                presenter?.signIn(editEmail.text.toString(), editPass.text.toString())
            }

        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }
    public fun showError(error : Int){
        when(error){
            ErrorsSigning.ERROR_EMAIL_NOT_VERIFIED->{
                Toast.makeText(activity?.baseContext,"Email not verified",Toast.LENGTH_SHORT).show()
            }
            ErrorsSigning.ERROR_LOGIN->{
                Toast.makeText(activity?.baseContext,"Login was failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    public fun goToStats(){
        findNavController().navigate(R.id.action_fragmentSignIn_to_myStatistics)

    }
    public fun showLoading(){
        layData.visibility = View.INVISIBLE
        layLoading.visibility = View.VISIBLE
    }
    public fun hideLoading(){
        layData.visibility = View.VISIBLE
        layLoading.visibility = View.INVISIBLE
    }
}