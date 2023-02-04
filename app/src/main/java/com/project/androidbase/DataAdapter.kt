package com.project.androidbase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.androidbase.api.DailyForecast
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")
class DataViewHolder(view: View, private val tempDisplaySettingManager: MenuSettingManager): RecyclerView.ViewHolder(view){
    // DataViewHolder is not a context cannot directly call findViewById NOT LIKE ACTIVITY
    // View/ViewGroup is a context
    private val tempText = view.findViewById<TextView>(R.id.tempText)
    private val descriptionText = view.findViewById<TextView>(R.id.tempDescription)
    private val dateText = view.findViewById<TextView>(R.id.dateText)
    private val forecastIcon = view.findViewById<ImageView>(R.id.forecastIcon)

    fun bind(data: DailyForecast){
        tempText.text = formatData(data.temp.max, tempDisplaySettingManager.getSettings())
        descriptionText.text = data.weather[0].description
        dateText.text = DATE_FORMAT.format(Date(data.date * 1000))

        val iconId = data.weather[0].icon
        // Use Picasso library to load img from url
        Picasso.get().load("http://openweathermap.org/img/wn/${iconId}@2x.png").into(forecastIcon)

    }
}


class DataAdapter(
    private val tempDisplaySettingManager: MenuSettingManager,
    // click each recycler view item handler
    private val clickHandler: (DailyForecast) -> Unit
): ListAdapter<DailyForecast,DataViewHolder>(DIFF_CONFIG){

    // statics
    companion object{
        // create an unnamed class/object
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                // === means exact same object reference
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                // == means content the same
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return DataViewHolder(itemView, tempDisplaySettingManager)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
        // Set click event
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }
}