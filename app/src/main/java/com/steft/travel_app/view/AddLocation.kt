package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.dataTestRita.User
import com.steft.travel_app.dataTestRita.UserViewModel
import kotlinx.coroutines.launch


class AddLocation : Fragment() {


    //try
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_location, container, false)

        //try
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        //Add Location - Save
        val createLocationBtn = view.findViewById<Button>(R.id.createNewLocationButton)

        createLocationBtn.setOnClickListener{

            //try
            //εισαγωγή στη βάση
            val cityLoc = view.findViewById<EditText>(R.id.addLocationCityET)?.text?.toString() ?: ""
            val countryLoc = view.findViewById<EditText>(R.id.addLocationCountryET)?.text?.toString() ?: ""
            //val typeLoc = view.findViewById<EditText>(R.id.editLocationType)?.text?.toString() ?: ""

            //val location = User(0, cityLoc, countryLoc, Integer.parseInt(typeLoc.toString()))
            //mUserViewModel.addUser(location)

            //stef
            /*viewLifecycleOwner.lifecycleScope.launch {
                service.insert(cityLoc, countryLoc, typeLoc)
                parentFragmentManager.popBackStack()
            }*/
            Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_LONG)

            findNavController().navigate(R.id.action_addLocation_to_locations)

        }

        return view
    }

}