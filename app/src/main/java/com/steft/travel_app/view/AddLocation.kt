package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R


class AddLocation : Fragment() {


    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_location, container, false)

        //Add Location - Save
        val saveLocation = view.findViewById<Button>(R.id.saveLocationButton)
        saveLocation.setOnClickListener{

            //εισαγωγή στη βάση
            insertDataToDatabase()
            findNavController().navigate(R.id.action_addLocation_to_locations)

        }

        return view
    }

    private fun insertDataToDatabase() {
        TODO("Not yet implemented")
    }


}