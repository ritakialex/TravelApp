package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R




class Bundle : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bundle, container, false)

        val view = inflater.inflate(R.layout.fragment_bundle, container, false)

        //Book Button Traveler
        val bookButton : Button = view.findViewById(R.id.bookBundleButton)
        bookButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_customerInfo)
        }

        //Edit Button Agent
        val editButton : Button = view.findViewById(R.id.editBundleButton)
        bookButton.setOnClickListener{
            //findNavController().navigate(R.id.action_bundle_to_edit----)
        }

        return view
    }


}
