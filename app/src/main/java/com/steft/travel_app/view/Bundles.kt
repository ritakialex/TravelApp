package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steft.travel_app.R
import com.steft.travel_app.placeholder.PlaceholderContent
import kotlinx.coroutines.launch

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
        val view = inflater.inflate(R.layout.fragment_bundles, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(items)
            }
        }
        return view
    }
}