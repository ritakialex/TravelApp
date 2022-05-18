package com.steft.travel_app.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.dataTestRita.User
import com.steft.travel_app.dataTestRita.UserViewModel
import com.steft.travel_app.databinding.FragmentAddLocationBinding
import com.steft.travel_app.databinding.FragmentRegisterBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import java.util.*


class AddLocation : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_add_location, container, false)

        val bind = FragmentAddLocationBinding.inflate(layoutInflater)

        //Add Location Button
        bind.createNewLocationButton.setOnClickListener {
            val city = bind.addLocationCity.text.toString()
            val country = bind.addLocationCountry.text.toString()

            try {
                viewModel
                    .addCustomLocation(city,country)
                Toast.makeText(context, "Created Successfully", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addLocation_to_locations)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }

        }

        return bind.root//return view
    }

}