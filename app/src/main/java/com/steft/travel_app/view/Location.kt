package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentLocationBinding
import com.steft.travel_app.viewmodel.MainViewModel
import java.util.*


class Location : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val bind = FragmentLocationBinding.inflate(layoutInflater)
        val args = this.arguments

        val locationId = args?.getString("locationId")


            try{
                if (locationId != null) {
                    val locationUUID = UUID.fromString(locationId)
                    viewModel
                        .getLocation(locationUUID)
                        .observe(viewLifecycleOwner) {
                            if (it != null) {
                                val (id, _, city, country) = it
                                bind.cityName.text = city
                                bind.countryName.text = country
                            } else {
                                throw Exception()
                            }
                        }
                }else {
                    throw Exception()
                }
            } catch (ex: Exception) {
                        //Do something
                        Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                        println(ex.message)
            }




        //navigation to Add new Bundle
        bind.addBundleButton.setOnClickListener {
            val bundle = bundleOf("locationId" to locationId)
            findNavController().navigate(R.id.action_location_to_addBundle,bundle)
        }

        //navigation to Delete location
        bind.locationDeleteButton.setOnClickListener {
            //todo delete location
            try{
                if (locationId != null) {
                    val locationUUID = UUID.fromString(locationId)
                    viewModel
                        .deleteCustomLocation(locationUUID)
                        .observe(viewLifecycleOwner) {
                            if (it) {
                                Toast.makeText(context, "Location deleted", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "You can't delete this Location", Toast.LENGTH_LONG).show()
                            }
                        }
                    findNavController().navigate(R.id.action_location_to_locations)
                }else {
                    throw Exception()
                }
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }

        }

        return bind.root
    }
}