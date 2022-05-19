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
import com.steft.travel_app.placeholder.PlaceholderContent
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


private fun MainViewModel.getBundles(locationId: UUID): LiveData<List<BundlePreviewDto>> =
    TODO()

class Bundles : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentBundlesListBinding.inflate(layoutInflater)
        val recyclerView = bind.recyclerBundlesList
        bind.floatingAddBundleButton.visibility = View.GONE

        try {
            val bundles =
                if (savedInstanceState != null) {
                    val locationId = savedInstanceState.getString("locationId")
                    if (locationId != null) {
                        val locationUUID = UUID.fromString(locationId)
                        viewModel.getBundles(locationUUID)
                    } else {
                        throw InconsistentStateException("You shouldn't be here. Call an administrator immediately!!")
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
                        adapter = MyItemRecyclerViewAdapter(ArrayList(bundles)) {
                            findNavController().navigate(R.id.action_bundles_to_bundle)
                        }
                    }
                }
        } catch (ex: Exception) {
            //Do something
            println(ex.message)
        }


        //button set VISIBLE if logged in
        try {
            if(viewModel.isLoggedIn()){
                        bind.floatingAddBundleButton.visibility = View.VISIBLE
                    } else {
                        throw Exception()
                    }
        } catch (ex: Exception) {
            //Do something
            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex.message)
        }


        bind.floatingAddBundleButton.setOnClickListener {
            findNavController().navigate(R.id.action_bundles_to_addBundle)
        }


        return bind.root
    }
}