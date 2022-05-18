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


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel by viewModels<MainViewModel> {
            MainViewModelFactory(application, true, UUID.randomUUID())
        }

        viewModel
            .registerCustomer(
                UUID.fromString("b6c8a409-1355-4948-b026-40fb86f788f4"),
                "Stef",
                "Touf",
                "+12124567890",
                "stefetoufe@gmail.com",
                "Hotel Grande")
//
//        val viewModel by viewModels<Ma>()
//
//        try {
//            viewModel
//                .register(
//                    "Validname",
//                    "2",
//                    "user",
//                    "123123123")
//
//        } catch (ex: Exception) {
//            println(ex.message)
//        }
//
//        try {
//            // for fragments
//            // val viewModel by activityViewModels<MainViewModel>()
//            viewModel2
//                .getLocations()
//                .observe(this) { locations -> locations.forEach(::println) }
//        } catch (ex: Exception) {
//            println(ex.message)
//        }
    }
}