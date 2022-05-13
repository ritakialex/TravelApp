package com.steft.travel_app.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentAgentLoginBinding
import com.steft.travel_app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentRegisterBinding.inflate(layoutInflater)

        bind.buttonRegister.setOnClickListener {
            val intent = Intent(this@RegisterFragment.requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }


        return bind.root //inflater.inflate(R.layout.fragment_register, container, false)
    }

}