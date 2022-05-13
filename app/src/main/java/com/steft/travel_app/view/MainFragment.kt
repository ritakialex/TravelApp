package com.steft.travel_app.view

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.steft.travel_app.R
import com.steft.travel_app.databinding.ActivityLoginBinding.inflate
import com.steft.travel_app.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
                ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val travelBtn = view.findViewById<Button>(R.id.travelerButton)
        travelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_locations)
        }

        return view


        //---------- Inflate the layout for this fragment
        /*val binding = FragmentMainBinding.inflate(layoutInflater)

        binding.travelerButton.setOnClickListener (
                Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_locations)
        *//*view : View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_locations)*//*
        )
        //setHasOptionsMenu(true)
        return binding.root
    }*/
    }
}