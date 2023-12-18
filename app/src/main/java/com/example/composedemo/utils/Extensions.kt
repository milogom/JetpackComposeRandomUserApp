package com.example.composedemo.utils

import android.content.Context
import android.widget.Toast
import com.example.composedemo.model.Location
import com.example.composedemo.model.Name
import com.example.composedemo.model.Registered

fun Context.ShowToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Name.getUsername(): String {
    return this.first + " " + this.last
}

fun Location.getUserLocation(): String {
    return this.street?.number.toString() + " " + this.street?.name + " " + (this.city) + " " + (this.state) + " " + (this.postcode) + " " + (this.country)
}

fun Location.getUserCoordinates(): String {
    return "Lat: " + this.coordinates?.latitude + ", Long: " + this.coordinates?.longitude
}

fun Location.getUserLatitude(): Double {
    return this.coordinates?.latitude?.toDouble() ?: 0.0
}

fun Location.getUserLongitude(): Double {
    return this.coordinates?.longitude?.toDouble() ?: 0.0
}

fun Registered.getRegisteredDate(): String {
    return this.date.toString()
}