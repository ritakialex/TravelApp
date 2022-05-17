package com.steft.travel_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.steft.travel_app.R
import com.steft.travel_app.common.InvalidObjectException
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
                as NavHostFragment

        navController = navHostFragment.navController

        //back arrow for navigation
        setupActionBarWithNavController(navController)






        //val viewModel2 by viewModels<MainViewModel>()


        /*try {
            viewModel
                .register(
                    "Validname",
                    "2",
                    "user",
                    "123123123")

        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            // for fragments
            // val viewModel by activityViewModels<MainViewModel>()
            viewModel2
                .getLocations()
                .observe(this) { locations -> locations.forEach(::println) }
        } catch (ex: Exception) {
            println(ex.message)
        }*/



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //pops msg: skipped no adapter attached

    /*val travelerBtn : Button = findViewById(R.id.travelerButton)
    travelerBtn.setOnClickListener {
        val locations = Locations()
        val locationsFr : Fragment? =
            supportFragmentManager.findFragmentByTag(Locations::class.java.simpleName)

        if (locationsFr !is Locations){
            supportFragmentManager.beginTransaction()
                .add(R.id.layoutMain, locations, Locations::class.java.simpleName)
                .commit()
        }
    }*/


    //Agent button- starts new activity to log in
    /*val agentBtn = findViewById<Button>(R.id.agencyButton)
    agentBtn.setOnClickListener {
        val Intent = Intent(this,LoginActivity::class.java)
        startActivity(Intent)
    }*/

    //τελικά θα πηγαίνω σε νέο fragment, όχι νέο activity


    /*val agentBtn = findViewById<Button>(R.id.agencyButton)
    agentBtn.setOnClickListener {
        val agentLogin = AgentLogin()
        val agentLoginFr : Fragment? =
        supportFragmentManager.findFragmentByTag(AgentLogin::class.java.simpleName)

        if (agentLoginFr !is AgentLogin){
            supportFragmentManager.beginTransaction()
                .add(R.id.layoutMain, agentLogin, AgentLogin::class.java.simpleName)
                .commit()
        }
        agentBtn.visibility = View.GONE
        travelerBtn.visibility = View.GONE
    }*/


    //αν στο mainActivity θελω να κάνω import fragment θα έχω ένα άδεια FrameLayout
    //κατω απο το setContentView
    //supportFragmentManager.beginTransaction().replace(R.id.nav_container, FirstFragment()).commit()


}