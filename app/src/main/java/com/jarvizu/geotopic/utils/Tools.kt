package com.jarvizu.geotopic.utils

import java.text.SimpleDateFormat
import java.util.*

// Small helper script to make the dates a little lice than ISO standard
fun getStringFormatted(datestring: String?): String? {
    val format = "dd/MM/yyyy"
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(datestring?.replace("-".toRegex(), "/")))
}