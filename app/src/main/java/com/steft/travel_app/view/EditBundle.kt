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
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.databinding.FragmentEditBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*


class EditBundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val bind = FragmentEditBundleBinding.inflate(layoutInflater)
        val args = this.arguments
        val bundleId = args?.getString("bundleId")

        //Save changes Button Agent
        bind.saveBundleButton.setOnClickListener {
            val bundleUUID = UUID.fromString(bundleId)
            val dateStr = bind.addBundleDate.text.toString() //to date
            val duration = Integer.parseInt(bind.addBundleDuration.text.toString())
            //val type = bind.radioGroup.checkedRadioButtonId//bind.addBundleType.text.toString().toInt() //how to convert into Location type
            val price = bind.addBundlePrice.text.toString().toDouble()
            val hotel1 = bind.addHotel1.text.toString()
            val hotel2 = bind.addHotel2.text.toString()
            val hotel3 = bind.addHotel3.text.toString() ?: ""
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

                val date = SimpleDateFormat("dd-mm-yyyy").parse(dateStr)
                    ?: throw IllegalArgumentException("Date doesn't conform to format dd-mm-yyyy")

                if (dateStr!= null && duration!=null && price!=null && hotel1!=null && hotel2!=null /*&& hotel3!=null*/) {
                    try {
                        viewModel
                            .updateBundle(
                                bundleUUID,
                                date = date,
                                duration = duration,
                                price = price,
                                hotels = hotels,
                                type = lType)
                    } catch (ex: Exception) {
                        Toast.makeText(
                            context,
                            "--make sure you filled all fields",
                            Toast.LENGTH_LONG).show()
                        println(ex.message)
                    }
                }
                Toast.makeText(requireContext(), "Changes saved!", Toast.LENGTH_LONG)
                findNavController().navigate(R.id.action_editBundle_to_bundles)

            }catch(ex: Exception) {
                Toast.makeText(context, "Oh well-----", Toast.LENGTH_LONG).show()
                println(ex.message)
            }
        }
        return bind.root
    }

}