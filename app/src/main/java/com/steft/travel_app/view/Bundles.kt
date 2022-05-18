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
            activity!!.application,
            true,
            UUID.fromString("df34b46c-5268-44f7-b213-c5a237447c3d"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bundles_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_bundles_list)!!


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
        val addBundleBtn : FloatingActionButton
        addBundleBtn = view.findViewById(R.id.floatingAddBundleButton)

        addBundleBtn.setOnClickListener{
            findNavController().navigate(R.id.action_bundles_to_addBundle)

        }


        return view
    }
}