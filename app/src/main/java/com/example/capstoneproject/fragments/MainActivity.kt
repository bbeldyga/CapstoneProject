package com.example.capstoneproject.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.capstoneproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.visibility = GONE
//
//        bottomNavigationView?.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                androidx.navigation.R.id.feedFragment -> {
//                    navController.navigate(androidx.navigation.R.id.optionsFragment, bundle)
//                    true
//                }
//                androidx.navigation.R.id.homeFragment -> {
//                    navController.navigate(androidx.navigation.R.id.helpFragment)
//                    true
//                }
//                androidx.navigation.R.id.blankFragment -> {
//                    navController.navigate(androidx.navigation.R.id.blankFragment)
//                    true
//                }
//                else -> false
//            }
//        }
    }
}
