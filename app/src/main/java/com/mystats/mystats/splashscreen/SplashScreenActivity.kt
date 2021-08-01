package com.mystats.mystats.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mystats.mystats.MainActivity.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        startActivity(Intent(this, MainActivity::class.java))
        super.onCreate(savedInstanceState)
    }
}