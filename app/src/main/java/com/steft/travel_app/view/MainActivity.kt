package com.steft.travel_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.steft.travel_app.R
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel by viewModels<MainViewModel>()

        // for fragments
        // val viewModel by activityViewModels<MainViewModel>()

        viewModel
            .getLocations(UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
            .observe(this) { locations -> locations.forEach(::println) }


    }
}