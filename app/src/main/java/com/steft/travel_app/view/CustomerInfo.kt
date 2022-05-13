package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R




class CustomerInfo : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_info, container, false)

        //Traveller
        val saveBtn = view.findViewById<Button>(R.id.saveButton)
        saveBtn.setOnClickListener {
            findNavController().navigate(R.id.action_customerInfo_to_locations)
        }

        return view
    }


}