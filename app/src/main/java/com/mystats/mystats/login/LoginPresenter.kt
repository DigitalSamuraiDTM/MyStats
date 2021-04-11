package com.mystats.mystats.login

import android.content.Context

class LoginPresenter {

    private var context: Context
    private var activity : LoginActivity
    constructor(context : Context, activity: LoginActivity){
        this.context = context;
        this.activity = activity;
    }

}