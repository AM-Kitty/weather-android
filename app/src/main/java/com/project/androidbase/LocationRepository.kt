package com.project.androidbase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// A sealed class is used for restricted access, only defined child class can extends its type
sealed class Location{
    data class Zipcode(val zipCode: String): Location()
}

private const val KEY_ZIPCODE = "key_zipcode"

class LocationRepository (context: Context){

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation

    // Observe any change for shared preferences
    init {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key!= KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener
            // Get the current zipcode for zipcode changed
            val zipcode = preferences.getString(KEY_ZIPCODE, "")
            if (zipcode != null && zipcode.isNotBlank()){
                _savedLocation.value = Location.Zipcode(zipcode)
            }
        }
        // Get the current zipcode to the Repository
        broadcastSavedZipcode()
    }

    fun saveLocation(location: Location){
        when(location){
            is Location.Zipcode -> preferences.edit().putString(KEY_ZIPCODE, location.zipCode).apply()
        }
    }

    private fun broadcastSavedZipcode(){
        val zipcode = preferences.getString(KEY_ZIPCODE, "")
        if (zipcode != null && zipcode.isNotBlank()){
            _savedLocation.value = Location.Zipcode(zipcode)
        }
    }
}