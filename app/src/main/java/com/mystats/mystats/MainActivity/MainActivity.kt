package com.mystats.mystats.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mystats.mystats.BaseFragmentInterface
import com.mystats.mystats.R

class MainActivity : AppCompatActivity(), BaseFragmentInterface  {

    private lateinit var hostFrame : FrameLayout
    private lateinit var fragmentSignUp: Fragment
    private lateinit var fragmentStartApp: Fragment
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        //navController = Navigation.findNavController(this,R.id.ac_main_nav_host)
    }

    override fun onDestroy() {

        super.onDestroy()
    }
    private fun init(){
        hostFrame = findViewById(R.id.ac_host_lay)
        fragmentStartApp = FragmentStartApp()
        //supportFragmentManager.beginTransaction().add(R.id.ac_host_lay,fragmentStartApp).commit()
    }
}