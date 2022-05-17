package com.steft.travel_app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.steft.travel_app.dto.LocationPreviewDto
import com.steft.travel_app.placeholder.PlaceholderContent
import com.steft.travel_app.placeholder.PlaceholderContent.PlaceholderItem
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class Locations : Fragment() {

    //    Testing data
//    private val items = liveData {
//        arrayListOf(
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece"),
//            LocationPreviewDto(UUID.randomUUID(), "Thessaloniki", "Thessaloniki, Greece")
//        ).let { emit(it) }
//    }
    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            activity!!.application,
            true,
            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_locations_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_locations_list)!!

        try {
            viewModel
                .getLocations()
                .observe(this) { locations ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MyItemRecyclerViewAdapter(ArrayList(locations))
                    }
                }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }


        //floating button
        val addLocationBtn: FloatingActionButton = view.findViewById(R.id.floatingAddLocationButton)

        addLocationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_locations_to_addLocation)
        }


        return view
    }

}