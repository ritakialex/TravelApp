package com.steft.travel_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.zip
import arrow.typeclasses.Semigroup
import com.steft.travel_app.R
import com.steft.travel_app.dao.RegistrationDaoImpl
import com.steft.travel_app.model.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

class MyViewModel : ViewModel() {

    fun doSome() {
        viewModelScope.launch {
            val dao = RegistrationDaoImpl()
////
//            val name = Name.makeValidated("Stef")
//            val surname = Name.makeValidated("Touf")
//            val phone = Phone.makeValidated("1 800 123 4567")
//            val email = Email.makeValidated("stefetoufe@gmail.com")
//            val hotel = "AAAA hotel"
//
//            val c1 = name.zip(Semigroup.nonEmptyList(), surname, phone, email)
//            { name, surname, phone, email ->
//                CustomerDetails(name, surname, phone, email, hotel)
//            }
//
//            val c2 = name.zip(Semigroup.nonEmptyList(), surname, phone, email)
//            { name, surname, phone, email ->
//                CustomerDetails(name, surname, phone, email, hotel)
//            }
//
//            c1.zip(Semigroup.nonEmptyList(), c2).map {
//
//                Registration(UUID.fromString("8e349217-0586-4375-b629-90f3e448e666"),
//                             it.toList())
//                    .let {
//                        dao.insert(it)
//                    }
//
//            }.fold(::println, ::println)

            dao.getAll().forEach { Log.i("Firebase", "Arrived: $it") }
        }
    }
}


class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.doSome()
    }
}