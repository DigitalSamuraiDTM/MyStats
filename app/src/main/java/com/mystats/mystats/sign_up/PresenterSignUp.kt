package com.mystats.mystats.sign_up

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class PresenterSignUp {
    private lateinit var view : FragmentSignUp
    constructor(view : FragmentSignUp){
        this.view = view;
    }

    fun signUpUser(email: String, pass : String, repPass : String){
        //todo проверка на пустые поля
        if (!pass.equals(repPass)){
            view.showError(ErrorsSigning.ERROR_NOT_EQUAL_PASS)
            return
        }
        if (email.isEmpty() || pass.isEmpty()|| repPass.isEmpty()){
            view.showError(ErrorsSigning.ERROR_EMPTY_INPUT)
            return
        }


        view.showLoading()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful){
                    FirebaseAuth.getInstance().currentUser.sendEmailVerification()
                    view.signInComplete()
                } else{
                    view.hideLoading()
                    view.showError(ErrorsSigning.ERROR_REGISTRATION)
                }
            }

        })


    }
}