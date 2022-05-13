package com.steft.travel_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.steft.travel_app.R




class Bundle : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bundle, container, false)

        val view = inflater.inflate(R.layout.fragment_bundle, container, false)
        val bookButton : Button = view.findViewById(R.id.bookBundleButton)
        bookButton.setOnClickListener{
            val fragment = CustomerInfo()
            val transaction = fragmentManager?.beginTransaction()
            //οπου nav_container για αυτον ειναι ενα FrameLayout στο mainActivity που είναι blank
            //transaction?.replace(R.id.nav_container,fragment)?.commit()
        }
        return view
    }


}
