package com.test.codechallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupBottomNavMenu(findNavController(R.id.bottomNavFragment))

    }

    private fun setupBottomNavMenu(navController: NavController) {

        bottomNavigationView?.setupWithNavController(navController)
    }


}
