package com.example.composedemo.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composedemo.R

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val fontOswaldSemiBold = FontFamily(
    Font(R.font.oswaldsemibold, FontWeight.Normal)
)

val fontOpenSansRegular = FontFamily(
    Font(R.font.opensansregular)
)

val fontOpenSansSemibold = FontFamily(
    Font(R.font.opensanssemibold)
)
