package com.steft.travel_app.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.steft.travel_app.databinding.FragmentLocationsBinding
import com.steft.travel_app.placeholder.PlaceholderContent.PlaceholderItem


class MyItemRecyclerViewAdapter(private val values: ArrayList<PlaceholderItem>) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FragmentLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentLocationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size


}