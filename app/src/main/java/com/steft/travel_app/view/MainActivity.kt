package com.steft.travel_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import com.steft.travel_app.R
import com.steft.travel_app.dao.AppDatabase
import com.steft.travel_app.dao.firebaseDb
import com.steft.travel_app.dao.registrationDao
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            application,
            intent.extras?.getString("id")?.let { UUID.fromString(it) }
//            UUID.fromString("bb4322c7-6341-4cd7-9865-12cbebe3b371")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
                as NavHostFragment

        navController = navHostFragment.navController

        //back arrow for navigation
        setupActionBarWithNavController(navController)

        viewModel.init()


    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}