package com.steft.travel_app.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.fx.coroutines.fixedRate
import com.steft.travel_app.R
import com.steft.travel_app.databinding.ActivityLoginBinding.inflate
import com.steft.travel_app.databinding.FragmentAgentLoginBinding
import com.steft.travel_app.viewmodel.LoginRegisterViewModel
import com.steft.travel_app.viewmodel.MainViewModel
import com.steft.travel_app.viewmodel.MainViewModelFactory
import java.util.*


class AgentLogin : Fragment() {

    private val viewModel by activityViewModels<LoginRegisterViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //αν πατήσει login σε νέο activity
        val bind = FragmentAgentLoginBinding.inflate(layoutInflater)


        bind.loginButton.setOnClickListener {
            val username = bind.usernamelogin.text.toString()
            val password = bind.passowordlogin.text.toString()

            try {
                viewModel
                    .login(username,password)
                    .observe(viewLifecycleOwner) {
                            if(it){
                                println("-------TEST---------True")
                                val intent = Intent(this@AgentLogin.requireContext(),LoginActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(context, "Wrong credentials", Toast.LENGTH_LONG).show()
                            }
                        }
            } catch (ex: Exception) {
                //Do something
                println(ex.message)
            }


        }

        //αν πατήσει register σε νέο fragment
        bind.gotoRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_agentLogin_to_registerFragment)
            /*
                val register = RegisterFragment()
                val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.agentLoginLayout, register)
                transaction.commit()
            */
        }


        // Inflate the layout for this fragment
        return bind.root //inflater.inflate(R.layout.fragment_agent_login, container, false)
    }


}