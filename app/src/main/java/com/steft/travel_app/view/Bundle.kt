@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.view

import android.os.Bundle
import android.text.method.KeyListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
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
        try {
            val bundleId =
                args?.getString("bundleId")
                    ?.let { UUID.fromString(it) }
                    ?: throw IllegalStateException("Bundle id should exist")

            viewModel
                .getBundle(bundleId)
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        val (_, agencyId, locationId, date, price, duration, hotels, type) = it
                        bind.dateFromBundleTextView.setText(date.toString())
                        bind.durationFromBundleTextView.setText(duration.toString())
                        bind.typeBundleTextView.setText(type)
                        bind.priceBundleTextView.setText(price.toString())

                        bind.hotelsBundleTextView.setText(hotels.tail()
                            .fold(hotels.first()) { acc, next -> "$acc -\n$next" })

                        //location info
                        viewModel.getLocation(locationId).observe(viewLifecycleOwner) { location ->
                            val location = location
                                ?: throw IllegalStateException("Location id should exist")

                            val (_, _, city, country) = location
                            bind.locationBundleInfo.text = "Bundle for $city, $country"
                        }
                    } else {
                        throw IllegalStateException("Bundle id cant be null")
                    }
                }
        } catch (ex: Exception) {
            Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex.message)
        }


        //Book Button Traveler
        bind.bookBundleButton.setOnClickListener {
            val bundleId = args?.getString("bundleId")
            val bundle = bundleOf("bundleId" to bundleId)
            findNavController().navigate(R.id.action_bundle_to_customerInfo, bundle)
        }



        //Edit - Save Button Agent
        if (viewModel.isLoggedIn()) {
            //visibility and Editabily changes
            bind.saveChangesBundleButton.visibility = View.VISIBLE
            bind.deleteBundleButton.visibility = View.VISIBLE
            bind.bookBundleButton.visibility = View.GONE
            bind.dateFromBundleTextView.isEnabled = true
            bind.durationFromBundleTextView.isEnabled = true
            bind.priceBundleTextView.isEnabled = true
            bind.hotelsBundleTextView.isEnabled = true
        }
        bind.saveChangesBundleButton.setOnClickListener {
            //TODO() call editBundle

            findNavController().navigate(R.id.action_bundle_to_bundles)
        }
        bind.deleteBundleButton.setOnClickListener {
            //TODO() call deletebundle
            Toast.makeText(context, "Bundle deleted", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_bundle_to_bundles)
        }



        return bind.root
    }


}
