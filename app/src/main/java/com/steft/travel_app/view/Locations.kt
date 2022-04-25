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
import com.steft.travel_app.dto.AppDatabase
import com.steft.travel_app.placeholder.PlaceholderContent
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class Locations : Fragment() {

    private var columnCount = 15

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_locations_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter = MyItemRecyclerViewAdapter(ArrayList(PlaceholderContent.ITEMS))
                }
            }
        }
        return view
    }

}