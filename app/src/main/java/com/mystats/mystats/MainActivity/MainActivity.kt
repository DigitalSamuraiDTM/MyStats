package com.mystats.mystats.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mystats.mystats.R

class MainActivity : AppCompatActivity()  {

    private lateinit var hostFrame : ConstraintLayout
    private lateinit var fragmentSignUp: Fragment
    private lateinit var fragmentStartApp: Fragment
    private lateinit var navController : NavController
    private lateinit var navView : BottomNavigationView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        init()
        navView = findViewById(R.id.act_main_bottonNavView)
        navController = Navigation.findNavController(this,R.id.ac_main_nav_host)
        NavigationUI.setupWithNavController(navView,navController)
    }

    override fun onDestroy() {

        super.onDestroy()
    }
    private fun init(){
        hostFrame = findViewById(R.id.ac_host_lay)
        fragmentStartApp = FragmentStartApp()
        //supportFragmentManager.beginTransaction().add(R.id.ac_host_lay,fragmentStartApp).commit()
    }
    public fun EnableBars(enable : Boolean){
        if (enable){
            supportActionBar?.show()
            navView.visibility = View.VISIBLE
        } else{
            supportActionBar?.hide()
            navView.visibility = View.GONE

        }
    }
}