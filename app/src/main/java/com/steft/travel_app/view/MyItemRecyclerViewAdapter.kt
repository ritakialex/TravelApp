package com.steft.travel_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.Shapeable
import com.steft.travel_app.R
//import com.steft.travel_app.databinding.FragmentLocationsBinding
import com.steft.travel_app.placeholder.PlaceholderContent.PlaceholderItem


class MyItemRecyclerViewAdapter(private val values: ArrayList<PlaceholderItem>) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

/*    inner class ViewHolder(binding: FragmentLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }*/

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //titleImage
        val itemImage : ShapeableImageView = itemView.findViewById(R.id.item_image)
        val itemTitle : TextView = itemView.findViewById(R.id.item_title)
        val itemDesc : TextView = itemView.findViewById(R.id.item_description)

    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentLocationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(itemView)
    }

    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = values[position]

        /*
        holder.itemImage.setImageResource(currentItem.itemImage)
        holder.itemTitle.text = currentItem.header
        */
    }


    override fun getItemCount(): Int = values.size


}