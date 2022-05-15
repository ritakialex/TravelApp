package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R


class AddBundle : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_bundle, container, false)

        //Add Bundle - Create
        val createBundleBtn = view.findViewById<Button>(R.id.createNewBundleButton)

        createBundleBtn.setOnClickListener{


            Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_LONG)

            findNavController().navigate(R.id.action_addBundle_to_bundles)
        }


        return view
    }


}