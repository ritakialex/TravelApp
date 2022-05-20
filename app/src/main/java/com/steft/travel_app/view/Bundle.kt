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
        val bundleId = 1 //GET UUID

        //show bundle
        /* try {
             viewModel
                 .getBundle(bundleId)
                 .observe(viewLifecycleOwner) {
                     if (it != null) {
                         val (_, agencyId, locationId, date, price, duration, hotels, type ) = it
                         bind.dateFromBundleTextView.text = date.toString()
                         bind.durationBundleTextView.text = duration.toString()
                         bind.typeBundleTextView.text = type
                         bind.priceBundleTextView.text = price.toString()
                         bind.agencyBundleTextView.text = agencyId.toString()
                         //TODO hotels List
                         bind.hotelsTV.text = hotels.toString()

                         viewModel.getLocation(locationId).observe(viewLifecycleOwner) {
                             val (id, agency, city, country) = it
                             bind.cityBundleTextView.text = city
                             bind.countryBundleTextView.text = country
                         }
                     } else {
                         throw Exception()
                     }
                 }
         }    catch (ex: Exception) {
             //Do something
             Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
             println(ex.message)
         }*/


        //Book Button Traveler
        bind.bookBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_customerInfo)
        }

        //Edit Button Agent
        /*bind.editBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_editBundle)
        }*/



        return bind.root
    }


}
