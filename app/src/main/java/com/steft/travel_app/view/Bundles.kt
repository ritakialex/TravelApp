package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentBundlesListBinding
import com.steft.travel_app.databinding.FragmentLocationsListBinding
import com.steft.travel_app.placeholder.PlaceholderContent
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*

class Bundles : Fragment() {
//    private val items = arrayListOf(
//        PlaceholderContent.PlaceholderItem("asd", "dasdawdwa", "details"),
//        PlaceholderContent.PlaceholderItem("asd", "12323asdsad", "details"),
//        PlaceholderContent.PlaceholderItem("asd", "aasdassd", "details")
//    )

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            requireActivity().application,
            true,
            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentBundlesListBinding.inflate(layoutInflater)
        val recyclerView = bind.recyclerBundlesList


        try {
            viewModel
                .getBundles()
                .observe(viewLifecycleOwner) { bundles ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MyItemRecyclerViewAdapter(ArrayList(bundles))
                    }
                }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }

        //floating button
        bind.floatingAddBundleButton.setOnClickListener{
            findNavController().navigate(R.id.action_bundles_to_addBundle)

        }


        return bind.root
    }
}