package com.steft.travel_app.view

import android.content.Intent
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
import com.steft.travel_app.databinding.FragmentCustomerInfoBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class CustomerInfo : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bind = FragmentCustomerInfoBinding.inflate(layoutInflater)
        val args = this.arguments



        //Traveller Book bundle
        bind.bookBundleCustInfoButton.setOnClickListener {
            try {
                val bundleId =
                    args?.getString("bundleId")
                        ?.let { UUID.fromString(it) }
                        ?: throw IllegalArgumentException("Bundle id should exist")


                    val name = bind.custName.text.toString()
                    val lastname = bind.custLName.text.toString()
                    val email = bind.custEmail.text.toString()
                    val mobile = bind.custMobile.text.toString()
                    val hotel = bind.custHotel.text.toString()


                    try {
                        viewModel
                            .registerCustomer(bundleId, name,lastname,mobile,email,hotel) //TODO: handle result

                        Toast.makeText(context, "Bundle Booked Successfully", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_customerInfo_to_locations)
                    } catch (ex: Exception) {
                        //Do something
                        Toast.makeText(context, "Input incorrect, try again", Toast.LENGTH_LONG).show()
                        println(ex.message)
                    }
            } catch (ex: Exception) {
                Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }

            //findNavController().navigate(R.id.action_customerInfo_to_locations)
        }

        return bind.root
    }


}