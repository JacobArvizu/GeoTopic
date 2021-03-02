package com.jarvizu.geotopic.data

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jarvizu.geotopic.R
import com.jarvizu.geotopic.databinding.PlaceItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class PlaceItem : AbstractBindingItem<PlaceItemBinding>() {
    var name: String? = "Name"
    var address: String? = "Address"

    override val type: Int
        get() = R.id.placeItem

    override fun bindView(binding: PlaceItemBinding, payloads: List<Any>) {
        binding.placeName.text = name
        binding.placeAddress.text = address
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PlaceItemBinding {
        return PlaceItemBinding.inflate(inflater, parent, false)
    }
}