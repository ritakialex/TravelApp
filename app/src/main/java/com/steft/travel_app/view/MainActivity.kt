package com.steft.travel_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.steft.travel_app.R
import com.steft.travel_app.common.InvalidObjectException
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*

val travelAgencyId: UUID = TODO()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel2 by viewModels<MainViewModel> {
            MainViewModelFactory(application, true, travelAgencyId)
        }

        val viewModel by viewModels<LoginRegisterViewModel>()

        try {
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
        }
    }
}