package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentEditBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel


class EditBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val bind = FragmentEditBundleBinding.inflate(layoutInflater)

        //Save changes Button Agent
        bind.saveBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Changes saved!", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_editBundle_to_bundles)
        }

        //Delete Button Agent
        bind.deleteBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Bundle Deleted!!", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_editBundle_to_bundles)
        }


        return bind.root
    }

}