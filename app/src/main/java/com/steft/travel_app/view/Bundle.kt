package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentAddLocationBinding
import com.steft.travel_app.databinding.FragmentBundleBinding
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*

class Bundle : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            requireActivity().application,
            true,
            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentBundleBinding.inflate(layoutInflater)

        //Book Button Traveler
        bind.bookBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_customerInfo)
        }

        //Edit Button Agent
        bind.editBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundle_to_editBundle)
        }



        return bind.root
    }


}
