package com.example.composedemo.utils

import android.content.Context
import com.example.composedemo.R
import java.text.SimpleDateFormat
import java.util.*

fun formatToShortDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date ?: return "")
}

fun showGenderTranslated(context: Context, gender: String): String {
    return when (gender) {
        "male" -> context.getString(R.string.male)
        "female" -> context.getString(R.string.female)
        else -> context.getString(R.string.indeterminate)
    }
}

