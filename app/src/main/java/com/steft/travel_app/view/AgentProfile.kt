package com.steft.travel_app.view

import android.content.Intent
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
import com.steft.travel_app.databinding.FragmentAgentLoginBinding
import com.steft.travel_app.databinding.FragmentAgentProfileBinding
import com.steft.travel_app.viewmodel.MainViewModel


class AgentProfile : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //αν πατήσει login σε νέο activity
        val bind = FragmentAgentProfileBinding.inflate(layoutInflater)

        try {
            viewModel
                .getTravelAgency()
                .observe(viewLifecycleOwner) {
                    if(it != null){
                        val (id, name, address, username) = it
                        bind.profileName.text = name
                        bind.profileAddress.text = address
                        bind.profileUsername.text = username
                    }else{
                        throw Exception()
                    }

                }
        } catch (ex: Exception) {
            //Do something
            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex.message)
        }


        //Logout
        bind.profileLogoutButton.setOnClickListener {
            val intent = Intent(this@AgentProfile.requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        //Delete
        bind.profileDeleteButton.setOnClickListener {
            TODO()
            /*try {
                viewModel
                    .deleteTravelAgency(id)
                Toast.makeText(context, "Profile Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(this@AgentProfile.requireContext(),MainActivity::class.java)
                startActivity(intent)
            } catch (ex: Exception) {
                //Do something
                Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
                println(ex.message)
            }*/
        }

        // Inflate the layout for this fragment
        return bind.root //return inflater.inflate(R.layout.fragment_agent_profile, container, false)
    }





}