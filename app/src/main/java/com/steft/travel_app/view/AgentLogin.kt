package com.steft.travel_app.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import arrow.fx.coroutines.fixedRate
import com.steft.travel_app.R
import com.steft.travel_app.databinding.ActivityLoginBinding.inflate
import com.steft.travel_app.databinding.FragmentAgentLoginBinding


class AgentLogin : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //αν πατήσει login σε νέο activity
        val bind = FragmentAgentLoginBinding.inflate(layoutInflater)

        bind.loginButton.setOnClickListener {
            val intent = Intent(this@AgentLogin.requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }



        //αν πατήσει register σε νέο fragment
        bind.gotoRegisterButton.setOnClickListener {
            val register = RegisterFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.agentLoginLayout, register)
            transaction.commit()
        }


        // Inflate the layout for this fragment
        return bind.root //inflater.inflate(R.layout.fragment_agent_login, container, false)
    }


}