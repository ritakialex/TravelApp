@file:Suppress("NestedLambdaShadowedImplicitParameter", "NAME_SHADOWING", "SimpleRedundantLet")

package com.steft.travel_app.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.steft.travel_app.R
import com.steft.travel_app.databinding.FragmentAgentLoginBinding
import com.steft.travel_app.databinding.FragmentRegisterBinding
import com.steft.travel_app.viewmodel.LoginRegisterViewModel

class RegisterFragment : Fragment() {

    private val viewModel by activityViewModels<LoginRegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bind = FragmentRegisterBinding.inflate(layoutInflater)

        bind.agencyName.setOnClickListener {
            Toast.makeText(context, "Start with capital letter", Toast.LENGTH_SHORT).show()
        }

        bind.buttonRegister.setOnClickListener {
            val name = bind.agencyName.text.toString()
            val address = bind.agencyAddress.text.toString()
            val username = bind.agencyUsername.text.toString()
            val password = bind.agencyPassword.text.toString()

            if (name != "" && address != "" && username != "" && password != "") {
                try {
                    viewModel
                        .register(name, address, username, password)
                        .observe(this) {
                            if (it) {

                                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG).show()

                                viewModel.login(username, password)
                                    .observe(this) { id ->
                                        val id = id
                                            ?.let { it.toString() }
                                            ?: error("Id cant be null if the registration has just happened successfully")
                                        val intent =
                                            Intent(this@RegisterFragment.requireContext(), LoginActivity::class.java)
                                        val b = Bundle()
                                        b.putString("id", id) //Your id
                                        intent.putExtras(b) //Put your id to your next Intent
                                        startActivity(intent)
                                    }

                            } else {
                                Toast.makeText(context, "Wrong credentials", Toast.LENGTH_LONG).show()
                            }
                        }

                } catch (ex: Exception) {
                    //Do something
                    Toast.makeText(context, "Input incorrect, try again", Toast.LENGTH_LONG).show()
                    println(ex.message)
                }
            } else {
                Toast.makeText(context, "fill all the fields", Toast.LENGTH_LONG).show()
            }

        }


        return bind.root //inflater.inflate(R.layout.fragment_register, container, false)
    }

}