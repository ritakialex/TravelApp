package com.steft.travel_app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.Placeholder
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentLocationsListBinding
import com.steft.travel_app.dto.LocationPreviewDto
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class Locations : Fragment() {


    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentLocationsListBinding.inflate(layoutInflater)

        //val view = inflater.inflate(R.layout.fragment_locations_list, container, false)
        //val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_locations_list)!!
        val recyclerView = bind.recyclerLocationsList
        bind.floatingAddLocationButton.visibility = View.GONE

        //view location - different navigation if location is selected

        fun showLocations(locations: List<LocationPreviewDto>, navigateTo: Int) =
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context)
                //for agent
                adapter =
                    MyItemRecyclerViewAdapter(ArrayList(locations)) { (id, _, _) ->
                        findNavController().navigate(navigateTo,
                            Bundle().also {
                                it.putString("locationId", id.toString())
                            })
                    }
                //for traveller

            }

        try {
            if (viewModel.isLoggedIn()) {
                viewModel
                    .getAgencyLocations()
                    .observe(viewLifecycleOwner) { showLocations(it, R.id.action_locations_to_location) }
            } else {
                viewModel
                    .getLocations()
                    .observe(viewLifecycleOwner) { showLocations(it, R.id.action_locations_to_bundles) }
            }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }


        //floating button set VISIBLE if logged in
        //val addLocationBtn: FloatingActionButton = view.findViewById(R.id.floatingAddLocationButton)
        if (viewModel.isLoggedIn()) {
            bind.floatingAddLocationButton.visibility = View.VISIBLE
        }

        bind.floatingAddLocationButton.setOnClickListener {
            findNavController().navigate(R.id.action_locations_to_addLocation)
        }

        return bind.root
    }

}