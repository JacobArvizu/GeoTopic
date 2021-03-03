package com.jarvizu.geotopic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavArgs(val location: String, val radius: String, val keyword: String) : Parcelable {
    override fun toString(): String {
        return super.toString()
    }
}