package com.steft.travel_app.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.steft.travel_app.R
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*

class LoginActivity : AppCompatActivity() {


    private lateinit var navController: NavController

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            application,
            intent.extras!!.getString("id").let { UUID.fromString(it) }
        )
//            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.init()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        //shows the title of the Fragment
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

//----------old code with drawer menu
    /*drawerLayout = findViewById(R.id.drawerLayout)
    val navView : NavigationView = findViewById(R.id.nav_view)

    toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    navView.setNavigationItemSelectedListener {


        //to highlight selected item
        it.isChecked = true

        when(it.itemId){
            R.id.nav_home -> replaceFragment(Bookings(),it.title.toString())
            R.id.nav_bookings -> replaceFragment(Bookings(),it.title.toString())
            R.id.nav_locations -> replaceFragment(Locations(),it.title.toString())
            //the name of the .kt of the fragment
            R.id.nav_bundles -> replaceFragment(Bundles(),it.title.toString())
            R.id.nav_profile -> replaceFragment(AgentProfile(),it.title.toString())
            R.id.nav_logout -> replaceFragment(AgentLogin(),it.title.toString())
            //R.id.nav_purchase -> Toast.makeText(applicationContext, "lala", Toast.LENGTH_SHORT).show()
        }

        true
    }

}

private fun replaceFragment(fragment: Fragment, title: String){

    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.frameLayout, fragment)
    fragmentTransaction.commit()

    drawerLayout.closeDrawers()
    setTitle(title)

}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (toggle.onOptionsItemSelected(item)){
        return true
    }

    return super.onOptionsItemSelected(item)
}*/
}