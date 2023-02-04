package com.project.androidbase.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.androidbase.*
import com.project.androidbase.api.DailyForecast
import com.project.androidbase.api.WeeklyForecast


/**
 * A simple [Fragment] subclass.
 * Use the [FutureDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FutureDataFragment : Fragment() {
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

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_future_data, container, false)
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        locationEntryButton.setOnClickListener{
            showLocationEntry()
        }

        val dataList: RecyclerView = view.findViewById(R.id.dataList)
        dataList.layoutManager = LinearLayoutManager(requireContext())

        // when the last parameter is a function you can do an outside parenthesis for that function paras
        // otherwise DataAdapter({})
        val dataAdapter = DataAdapter(tempDisplaySettingManager){
//            showDataDetails(it)
            showFragmentDetails(it)
        }
        dataList.adapter = dataAdapter

//         Observe any change
//        val dataObserver = Observer<List<DataModel>> { dataItems ->
//            // update our list adapter
//            dataAdapter.submitList(dataItems)
//        }
//        dataRepository.futureData.observe(viewLifecycleOwner, dataObserver)

        val weeklyForecastObserver = Observer<WeeklyForecast>{
            emptyText.visibility = View.GONE
            progressBar.visibility = View.GONE

            // submitList method required List<>
            dataAdapter.submitList(it.daily)
        }
        dataRepository.futureData.observe(viewLifecycleOwner, weeklyForecastObserver)

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{
            when(it){
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE
                    dataRepository.loadFutureData(it.zipCode)
                }
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

//        Instantiate and load data
//        dataRepository.loadData()
        return view
    }

    private fun showLocationEntry(){
        val action = FutureDataFragmentDirections.actionFutureDataFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showFragmentDetails(data: DailyForecast){
        val action = FutureDataFragmentDirections.actionFutureDataFragmentToSecondFragment(data.temp.max, data.weather[0].description)
        findNavController().navigate(action)
    }

    companion object{
        const val KEY_ZIPCODE = "key_zipcode"
        fun newInstance(zipcode: String): FutureDataFragment{
            val fragment = FutureDataFragment()
            // Defined to store key-value pairs, pass things to intents or to pass around with fragment arguments
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)

            fragment.arguments = args
            return fragment
        }
    }

}