package com.project.androidbase.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.project.androidbase.*
import com.project.androidbase.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentDataFragment : Fragment() {
    private val dataRepository = DataRepository()
    private lateinit var tempDisplaySettingManager: MenuSettingManager
    private lateinit var locationRepository: LocationRepository

//    private lateinit var appNavigator: AppNavigator
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        // 'as' cast variable to another type
//        appNavigator = context as AppNavigator
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // instantiate menu
        tempDisplaySettingManager = MenuSettingManager(requireContext())

        // value in this fragment will pass to another fragment
        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_data, container, false)
        val locationName = view.findViewById<TextView>(R.id.locationName)
        val tempText = view.findViewById<TextView>(R.id.tempText)
        val forecastIcon = view.findViewById<ImageView>(R.id.forecastIcon)

        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        locationEntryButton.setOnClickListener{
//            appNavigator.navigateToLocationEntry()
            showLocationEntry()
        }

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{ savedLocation ->
            // Adding when for extensibility for adding more types of Location
            when(savedLocation){
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE
                    dataRepository.loadCurrentData(savedLocation.zipCode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

//        RecyclerView Observer
//        val dataList: RecyclerView = view.findViewById(R.id.dataList)
//        dataList.layoutManager = LinearLayoutManager(requireContext())
//
//         when the last parameter is a function you can do an outside parenthesis for that function paras
//         otherwise DataAdapter({})
//        val dataAdapter = DataAdapter(tempDisplaySettingManager){
//            showFragmentDetails(it)
//        }
//
//        dataList.adapter = dataAdapter

        // Create the Observer which updates the UI in response to weather updates
        val dataObserver = Observer<CurrentWeather> { weather ->
            // update our list adapter with size 1 (one item in the list)
            // dataAdapter.submitList(listOf(dataItem))

            // When we have data, change visibility to GONE
            emptyText.visibility = View.GONE
            locationName.visibility = View.VISIBLE
            tempText.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            // Observe change of value weather
            locationName.text = weather.name
            tempText.text = formatData(weather.forecast.temp, tempDisplaySettingManager.getSettings())

            // Use Picasso library to load img from url
            Picasso.get().load("http://openweathermap.org/img/wn/02d@2x.png").into(forecastIcon)

        }
        dataRepository.currentData.observe(viewLifecycleOwner, dataObserver)

//         Instantiate and load data
//        dataRepository.loadData()
        return view
    }

    private fun showLocationEntry(){
        val action = CurrentDataFragmentDirections.actionCurrentDataFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

//    private fun showFragmentDetails(data: DataModel){
////        appNavigator.navigateToSecondFragment(data)
//        val action = CurrentDataFragmentDirections.actionCurrentDataFragmentToSecondFragment(data.data, data.description)
//        findNavController().navigate(action)
//    }

//     Using Intent to pass to 2nd activity
//    private fun showDataDetails(data: DataModel){
//        // Navigate to 2nd activity
//        val secondActivityIntent = Intent(requireContext(), DoubleActivity::class.java)
//         Pass parameters to Intent
//        secondActivityIntent.putExtra("key_text", data.data)
//        secondActivityIntent.putExtra("ket_descript", data.description)
//        startActivity(secondActivityIntent)
//    }

    companion object{
        const val KEY_ZIPCODE = "key_zipcode"
        fun newInstance(zipcode: String): CurrentDataFragment{
            val fragment = CurrentDataFragment()
            // Defined to store key-value pairs, pass things to intents or to pass around with fragment arguments
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)

            fragment.arguments = args
            return fragment
        }
    }

}