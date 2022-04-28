package com.steft.travel_app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steft.travel_app.R
import com.steft.travel_app.placeholder.PlaceholderContent
import com.steft.travel_app.placeholder.PlaceholderContent.PlaceholderItem
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class Locations : Fragment() {


    private val items = arrayListOf(
        PlaceholderItem("asd", "aasdassd", "details"),
        PlaceholderItem("asd", "aasdassd", "details"),
        PlaceholderItem("asd", "aasdassd", "details")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_locations_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(items)
            }
        }
        return view
    }

}