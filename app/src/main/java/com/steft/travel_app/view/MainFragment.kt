package com.steft.travel_app.view

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.steft.travel_app.R
import com.steft.travel_app.databinding.ActivityLoginBinding.inflate
import com.steft.travel_app.databinding.FragmentMainBinding
import com.steft.travel_app.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()
    override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
                ): View? {

        val bind = FragmentMainBinding.inflate(layoutInflater)

        //Traveller
        bind.travelerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_locations)
        }


        //Agent
        bind.agencyButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_agentLogin)
        }



        return bind.root


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