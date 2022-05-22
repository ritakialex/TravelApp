@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.steft.travel_app.R
import com.steft.travel_app.common.InconsistentStateException
import com.steft.travel_app.common.UnauthorizedException
import com.steft.travel_app.databinding.FragmentBundlesListBinding
import com.steft.travel_app.databinding.FragmentLocationsListBinding
import com.steft.travel_app.dto.BundlePreviewDto
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class Bundles : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentBundlesListBinding.inflate(layoutInflater)
        val recyclerView = bind.recyclerBundlesList
        val args = this.arguments


        try {
            val bundles =
                if (args != null) {
                    val locationId = args.getString("locationId")
                    if (locationId != null) {
                        val locationUUID = UUID.fromString(locationId)
                        viewModel.getBundles(locationUUID)
                    } else {
                        throw IllegalStateException("You shouldn't be here. Call an administrator immediately!!")
                    }
                } else if (viewModel.isLoggedIn()) {
                    viewModel.getAgencyBundles()
                } else {
                    throw UnauthorizedException("You shouldn't be here. Call an administrator immediately!!")
                }
            bundles
                .observe(viewLifecycleOwner) { bundles ->
                    with(recyclerView) {
                        layoutManager = LinearLayoutManager(context)
                        adapter = MyItemRecyclerViewAdapter(ArrayList(bundles)) { (id, _, _) ->
                            findNavController().navigate(R.id.action_bundles_to_bundle,
                                Bundle().also {
                                    it.putString("bundleId", id.toString())
                                })
                        }
                    }
                }
        } catch (ex: Exception) {
            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex)
        }

        //-----delete later TODO
        //button set VISIBLE if logged in
        /*if (viewModel.isLoggedIn())
            bind.floatingAddBundleButton.visibility = View.VISIBLE

        bind.floatingAddBundleButton.setOnClickListener {
            findNavController().navigate(R.id.action_bundles_to_addBundle)
        }*/


        return bind.root
    }
}