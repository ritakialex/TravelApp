package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentBookingsBinding
import com.steft.travel_app.databinding.FragmentLocationsListBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class Bookings : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            requireActivity().application,
            true,
            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bind = FragmentBookingsBinding.inflate(layoutInflater)
        val recyclerView = bind.recyclerBookingsList

        /*try {
            viewModel
                .getBookings()
                .observe(viewLifecycleOwner) { bookings ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MyItemRecyclerViewAdapter(ArrayList(bookings))
                    }
                }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }*/


        return bind.root
    }


}