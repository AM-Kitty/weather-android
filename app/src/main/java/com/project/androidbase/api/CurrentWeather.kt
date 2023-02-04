package com.project.androidbase.api

import com.squareup.moshi.Json

// Model for API response
data class Forecast(val temp: Float)

data class Coordinates(val lat:Float, val lon: Float)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast
)