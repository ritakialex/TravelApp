package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.steft.travel_app.R
import com.steft.travel_app.placeholder.PlaceholderContent

class Bundles : Fragment() {
    private val items = arrayListOf(
        PlaceholderContent.PlaceholderItem("asd", "dasdawdwa", "details"),
        PlaceholderContent.PlaceholderItem("asd", "12323asdsad", "details"),
        PlaceholderContent.PlaceholderItem("asd", "aasdassd", "details")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bundles_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(items)
            }
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