package com.jarvizu.geotopic.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jarvizu.geotopic.R

class PlaceAdapter(val places: List<PlacePojo.Result>) : RecyclerView.Adapter<PlacesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return PlacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        return holder.bind(places[position])
    }
}

class PlacesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: MaterialTextView = itemView.findViewById(R.id.place_name)
    private val address: MaterialTextView = itemView.findViewById(R.id.place_address)

    fun bind(place: PlacePojo.Result) {
        name.text = "Place Name: ${place.name}"
        address.text = "Place Address: ${place.formattedAddress}"
    }

}
