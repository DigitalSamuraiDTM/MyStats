package com.mystats.mystats.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mystats.mystats.BaseActivityInterface
import com.mystats.mystats.R

class LoginActivity : AppCompatActivity(), BaseActivityInterface  {
    private var presenter : LoginPresenter? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(applicationContext,this);
    }

    override fun onDestroy() {

        presenter = null
        super.onDestroy()
    }
}