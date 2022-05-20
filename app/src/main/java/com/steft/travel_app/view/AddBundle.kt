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
import com.steft.travel_app.databinding.FragmentAddBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val bind = FragmentAddBundleBinding.inflate(layoutInflater)
        val agency = viewModel.getId()

        println("------------------went to ADD Bundle----------")
        //Add Bundle - Create
        bind.createNewBundleButton.setOnClickListener{
            /*val loc_id = 1 //GET ID
            //val date = bind.addBundleDate.text.toString() //to date
            //val date = LocalDate.parse(bind.addBundleDate.text.toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy")) //to date
            val duration = Integer.parseInt(bind.addBundleDuration.text.toString())
            val type = bind.addBundleType.text.toString() //how to convert into Location type
            val price = bind.addBundlePrice.text.toString().toDouble()
            val hotel1 = bind.addHotel1.text.toString()
            val hotel2 = bind.addHotel2.text.toString()
            val hotel3 = bind.addHotel3.text.toString()
            val hotels = listOf<String>(hotel1,hotel2, hotel3)

            try {
                viewModel
                    .addBundle(loc_id,date, price, duration, hotels, type)
                Toast.makeText(context, "Created Successfully", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addLocation_to_locations)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }

            Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_LONG)*/

            findNavController().navigate(R.id.action_addBundle_to_bundles)
        }


        return bind.root
    }


}