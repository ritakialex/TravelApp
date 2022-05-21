package com.steft.travel_app.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.databinding.FragmentAddBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class AddBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val bind = FragmentAddBundleBinding.inflate(layoutInflater)
        //val agency = viewModel.getUserId()
        val args = this.arguments
        val locationId = args?.getString("locationId")

        //fill textView with location city and country
        try{
            if (locationId != null) {
                val locationUUID = UUID.fromString(locationId)
                viewModel
                    .getLocation(locationUUID)
                    .observe(viewLifecycleOwner) {
                        if (it != null) {
                            val (id, _, city, country) = it
                            bind.locationInfo.text = "Bundle for $city, $country"
                        } else {
                            throw Exception()
                        }
                    }
            }
        }catch(ex: Exception){
            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
        }





        //Add Bundle - Create
        bind.createNewBundleButton.setOnClickListener {

            try {
                val locationUUID = UUID.fromString(locationId)
                val dateStr = bind.addBundleDate.text.toString() //to date
                val duration = Integer.parseInt(bind.addBundleDuration.text.toString())
                //val type = bind.radioGroup.checkedRadioButtonId//bind.addBundleType.text.toString().toInt() //how to convert into Location type
                val price = bind.addBundlePrice.text.toString().toDouble()
                val hotel1 = bind.addHotel1.text.toString()
                val hotel2 = bind.addHotel2.text.toString()
                val hotel3 = bind.addHotel3.text.toString()
                val hotels = listOf(hotel1, hotel2, hotel3)


                var type = 0
                bind.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                    when (i) {
                        R.id.cruiseRadio -> type = 0
                        R.id.roadtripRadio -> type = 1
                        R.id.independentRadio -> type = 2
                    }
                }

                try {
                    val lType = when (type) {
                        0 -> LocationType.Cruise
                        1 -> LocationType.Roadtrip
                        2 -> LocationType.Independent
                        else -> throw IllegalArgumentException("Must be between 0 and 2")
                    }

                    val date =
                        SimpleDateFormat("dd-mm-yyyy")
                            .parse(dateStr)
                            ?: throw IllegalArgumentException("Date doesn't conform to format dd-mm-yyyy")

                    //try to create bundle
                    try {
                        viewModel
                            .addBundle(locationUUID, date, price, duration, hotels, lType) //TODO: Observe result
                        Toast.makeText(context, "Created Successfully", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_addBundle_to_bundles)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "make sure you filled all fields",
                            Toast.LENGTH_LONG).show()
                        println(ex.message)
                    }
                } catch (ex: Exception) {
                    Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG)
                        .show()
                    println(ex.message)
                }

                /*Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_LONG)
                findNavController().navigate(R.id.action_addBundle_to_bundles)*/
            }catch(ex: Exception){
                println(ex.message)
            }
        }


        return bind.root
    }


}