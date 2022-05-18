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
                    }else{
                        throw Exception()
                    }

                }
        } catch (ex: Exception) {
            //Do something
            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_LONG).show()
            println(ex.message)
        }



        bind.profileLogoutButton.setOnClickListener {
            val intent = Intent(this@AgentProfile.requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        bind.profileDeleteButton.setOnClickListener {
            TODO()
        }

        // Inflate the layout for this fragment
        return bind.root //return inflater.inflate(R.layout.fragment_agent_profile, container, false)
    }





}