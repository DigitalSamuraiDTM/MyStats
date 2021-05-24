package com.mystats.mystats.sign_in

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mystats.mystats.ErrorsSigning

class PresenterSignIn {
    private lateinit var view : FragmentSignIn
    constructor(view : FragmentSignIn){
        this.view = view
    }

    public fun signIn(email: String, pass : String){
        if (email.isEmpty() || pass.isEmpty()){
            view.showError(ErrorsSigning.ERROR_EMPTY_INPUT)
            return
        }
        view.showLoading()
        if (FirebaseAuth.getInstance().currentUser != null){
            // Синхронизирует пользователя с БД
            FirebaseAuth.getInstance().currentUser?.reload()?.addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {
                    if (p0.isSuccessful){
                        if (FirebaseAuth.getInstance().currentUser.isEmailVerified){
                            view.goToStats()
                        } else{
                            view.hideLoading()
                            view.showError(ErrorsSigning.ERROR_EMAIL_NOT_VERIFIED)
                        }
                    }
                }

            })
        } else{
            // Авторизация
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(object :  OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful){
                        view.goToStats()
                    } else{
                        view.showError(ErrorsSigning.ERROR_LOGIN)
                    }
                }

            });


        }

    }

    public fun sendVerifyMessage(){
        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnCompleteListener(object :  OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {

            }

        })
    }



}