package com.project.androidbase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.androidbase.api.CurrentWeather
import com.project.androidbase.api.WeeklyForecast
import com.project.androidbase.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {

    // Allows update data only in repository; decoupled
    private val _currentData = MutableLiveData<CurrentWeather>()
    val currentData: LiveData<CurrentWeather> = _currentData

    // Allows update data only in repository; decoupled
    private val _futureData = MutableLiveData<WeeklyForecast>()
    val futureData: LiveData<WeeklyForecast> = _futureData

    fun loadCurrentData(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null){
                    _currentData.value = weatherResponse
                }

            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(DataRepository::class.java.simpleName, "error loading current weather", t)
            }

        })

    }

    fun loadFutureData(zipcode: String) {
//        val randomData = List(25){ Random.nextFloat().rem(100) * 100}
//        val dataModels = randomData.map { temp ->
//            DataModel(Date(), temp, setDescription(temp))
//        }

        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null){
                    // load 7 day forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )

                    forecastCall.enqueue(object : Callback<WeeklyForecast>{
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse!=null){
                                _futureData.value = weeklyForecastResponse
                            }
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(DataRepository::class.java.simpleName, "error loading weekly forecast", t)
                        }

                    })
                }

            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(DataRepository::class.java.simpleName, "error loading location for weekly weather", t)
            }

        })

    }

    private fun setDescription(data: Float): String{
        return when(data){
            in Float.MIN_VALUE.rangeTo(0f) -> "Too Cold"
            in 0f.rangeTo(50f) -> "Medium"
            else -> "Default description"
        }
    }
}