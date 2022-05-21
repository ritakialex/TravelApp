package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentBookingsBinding
import com.steft.travel_app.databinding.FragmentLocationsListBinding
import com.steft.travel_app.dto.RegistrationPreviewDto
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*



class Bookings : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bind = FragmentBookingsBinding.inflate(layoutInflater)
        val recyclerView = bind.recyclerBookingsList

        //εμφάνιση των κρατήσεων
        try {
            viewModel
                .getAgencyBookings()
                .observe(viewLifecycleOwner) { bookings ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MyItemRecyclerViewAdapter(ArrayList(bookings)) {}
                    }
                }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }




        return bind.root
    }


}