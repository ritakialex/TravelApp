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
import java.util.*


class AddBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val bind = FragmentAddBundleBinding.inflate(layoutInflater)

        println("------------------went to ADD Bundle----------")
        //Add Bundle - Create
        bind.createNewBundleButton.setOnClickListener{

            val date = bind.addBundleDate.text.toString()
            val duration = bind.addBundleDuration.text.toString()
            val type = bind.addBundleType.text.toString()
            val price = bind.addBundlePrice.text.toString()

            /*try {
                viewModel
                    .addCustomLocation(city,country)
                Toast.makeText(context, "Created Successfully", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_addLocation_to_locations)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }*/

            Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_LONG)

            findNavController().navigate(R.id.action_addBundle_to_bundles)
        }


        return bind.root
    }


}