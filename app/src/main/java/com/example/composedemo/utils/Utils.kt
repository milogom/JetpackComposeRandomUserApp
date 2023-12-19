package com.example.composedemo.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.composedemo.R
import java.text.SimpleDateFormat
import java.util.*

fun formatToShortDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date ?: return "")
}

@Composable
fun showGenderTranslated(gender: String): String {
    return when (gender) {
        "male" -> stringResource(id = R.string.male)
        "female" -> stringResource(id = R.string.female)
        else -> stringResource(id = R.string.indeterminate)
    }
}

