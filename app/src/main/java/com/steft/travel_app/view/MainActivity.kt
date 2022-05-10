package com.steft.travel_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.steft.travel_app.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //pops msg: skipped no adapter attached
        val travelerBtn : Button = findViewById(R.id.travelerButton)
        travelerBtn.setOnClickListener {
            val locations = Locations()
            val locationsFr : Fragment? =
                supportFragmentManager.findFragmentByTag(Locations::class.java.simpleName)

            if (locationsFr !is Locations){
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayoutMain, locations, Locations::class.java.simpleName)
                    .commit()
            }
        }


        //Agent button starts new activity to log in
        val agentBtn = findViewById<Button>(R.id.agencyButton)
        agentBtn.setOnClickListener {
            val Intent = Intent(this,LoginActivity::class.java)
            startActivity(Intent)
        }



    }
}