@file:Suppress("NAME_SHADOWING")

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
import arrow.core.tail
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentAddLocationBinding
import com.steft.travel_app.databinding.FragmentBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*

class Bundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val bind = FragmentBundleBinding.inflate(layoutInflater)
        val args = this.arguments

        //show bundle
        /*try {
            val bundleId =
                args?.getString("bundleId")
                    ?.let { UUID.fromString(it) }
                    ?: throw IllegalStateException("Bundle id should exist")

            viewModel
                .getBundle(bundleId)
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        val (_, agencyId, locationId, date, price, duration, hotels, type) = it
                        bind.dateFromBundleTextView.text = date.toString()
                        bind.durationBundleTextView.text = duration.toString()
                        bind.typeBundleTextView.text = type
                        bind.priceBundleTextView.text = price.toString()
                        bind.agencyBundleTextView.text = agencyId.toString()
                        //TODO hotels List
                        bind.hotelsBundleTextView.text =
                            hotels.tail()
                                .fold(hotels.first()) { acc, next -> "$acc\n$next" }

                        viewModel.getLocation(locationId).observe(viewLifecycleOwner) { location ->
                            val location = location
                                ?: throw IllegalStateException("Location id should exist")

                            val (_, _, city, country) = location
                            bind.cityBundleTextView.text = city
                            bind.countryBundleTextView.text = country
                        }
                    } else {
                        throw IllegalStateException("Bundle id cant be null")
                    }
                }
        } catch (ex: Exception) {
            Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex.message)
        }*/


        //Book Button Traveler
        bind.bookBundleButton.setOnClickListener {
            findNavController().navigate(R.id.action_bundle_to_customerInfo)
        }

        //Edit - Save Button Agent
        bind.saveChangesBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_bundles)
        }



        return bind.root
    }


}
