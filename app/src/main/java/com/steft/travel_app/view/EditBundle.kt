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
import com.steft.travel_app.databinding.FragmentEditBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel


class EditBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val bind = FragmentEditBundleBinding.inflate(layoutInflater)

        //show bundle
       /* try {
            viewModel
                .getBundle(id)
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        val (id, agencyId, locationId, date, price, duration, hotels, type ) = it
                        bind.dateFromBundleEditText.text = date
                        bind.durationBundleEditText.text = duration
                        bind.typeBundleEditText.text = type
                        bind.priceBundleEditText.text = price
                        bind.agencyBundleEditText.text = agencyId

                        viewModel.getLocation(locationId).observe(viewLifecycleOwner) {
                            val (id, agency, city, country) = it
                            bind.cityEditText.text = city
                            bind.countryEditText.text = country
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



        //Save changes Button Agent
        bind.saveBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Changes saved!", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_editBundle_to_bundles)
            /*val date = bind.dateFromBundleEditText.text.toString()
            val duration = bind.durationBundleEditText.text.toString()
            val type = bind.typeBundleEditText.text.toString()
            val price = bind.priceBundleEditText.text.toString()
            try {
                viewModel
                    .saveBundle(id,date,duration,type, price)
                Toast.makeText(context, "Bundle Deleted", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_customerInfo_to_locations)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }*/
        }

        //Delete Button Agent
        bind.deleteBundleButton.setOnClickListener{
            Toast.makeText(requireContext(), "Bundle Deleted!!", Toast.LENGTH_LONG)
            /*try {
                viewModel
                    .deleteBundle(id)
                Toast.makeText(context, "Bundle Deleted", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_customerInfo_to_locations)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "Something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }*/
            findNavController().navigate(R.id.action_editBundle_to_bundles)
        }


        return bind.root
    }

}