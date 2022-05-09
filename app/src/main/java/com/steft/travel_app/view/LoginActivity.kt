package com.steft.travel_app.view

import android.icu.text.CaseMap
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.steft.travel_app.R

class LoginActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
     lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {


            //to highlight selected item
            it.isChecked = true

            when(it.itemId){
                R.id.nav_home -> Toast.makeText(applicationContext, "lala", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> Toast.makeText(applicationContext, "lala", Toast.LENGTH_SHORT).show()
                //the name of the .kt of the fragment
                R.id.nav_bundles -> replaceFragment(Bundles(),it.title.toString())
                R.id.nav_profile -> Toast.makeText(applicationContext, "lala", Toast.LENGTH_SHORT).show()
                R.id.nav_purchase -> Toast.makeText(applicationContext, "lala", Toast.LENGTH_SHORT).show()
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
    }
}