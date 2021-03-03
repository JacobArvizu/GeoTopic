package com.jarvizu.geotopic.data

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.EventItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class EventItem : AbstractBindingItem<EventItemBinding>() {
    var name: String? = "Name"
    var photo: String? = null
    var address: String? = "Address"
    var date: String? = "Date"
    var linkUrl: String? = "https://www.google.com/"

    override val type: Int
        get() = R.id.eventItem

    override fun bindView(binding: EventItemBinding, payloads: List<Any>) {
        binding.eventName.text = "Event Name: " + name
        binding.eventAddress.text = "Address:\n" + address
        // Load image into glide
        Glide
            .with(binding.root.context)
            .load(photo)
            .centerCrop()
            .placeholder(R.drawable.baseline_corporate_fare_black_24dp)
            .into(binding.eventImage);
        binding.eventDate.text = "Date:\n" + date
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): EventItemBinding {
        return EventItemBinding.inflate(inflater, parent, false)
    }
}