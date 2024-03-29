package com.steft.travel_app.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.steft.travel_app.R
import com.steft.travel_app.dto.Preview
import java.util.UUID


class MyItemRecyclerViewAdapter<T : Preview>(private val values: ArrayList<T>, private val onItemClick: (T) -> Unit) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    //-------
/*    inner class ViewHolder(binding: FragmentLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }*/

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //titleImage
        //val itemImage: ShapeableImageView = itemView.findViewById(R.id.item_image)
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemDesc: TextView = itemView.findViewById(R.id.item_description)

        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
//---------
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

    //------------
    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = values[position]

        with(holder) {
            itemTitle.text = currentItem.title
            itemDesc.text = currentItem.details
        }

        holder.cardView.setOnClickListener {
            onItemClick(currentItem)
        }

//        holder.itemImage.setImageResource(currentItem.itemImage)
    }


    override fun getItemCount(): Int = values.size


}