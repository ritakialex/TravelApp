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


class EditBundle : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_bundle, container, false)

        //Save changes Button Agent
        val saveBundleButton : Button = view.findViewById(R.id.saveBundleButton)
        saveBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Changes saved!", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_editBundle_to_bundle)
        }

        //Delete Button Agent
        val deleteBundleButton : Button = view.findViewById(R.id.deleteBundleButton)
        deleteBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Bundle Deleted!!", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_editBundle_to_bundles)
        }


        return view
    }

}