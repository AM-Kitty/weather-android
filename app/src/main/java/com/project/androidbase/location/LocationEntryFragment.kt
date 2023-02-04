package com.project.androidbase.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.androidbase.Location
import com.project.androidbase.LocationRepository
import com.project.androidbase.R

/**
 * A simple [Fragment] subclass.
 * Use the [LocationEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationEntryFragment : Fragment() {

//    private lateinit var appNavigator: AppNavigator
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        // 'as' cast variable to another type
//        appNavigator = context as AppNavigator
//    }

    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationRepository = LocationRepository(requireContext())

        // From here to get the actual parameter value zipCode
        val zipCode = "Zipcode"

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val zipCodeEditText = view.findViewById<EditText>(R.id.textInput)

        submitButton.setOnClickListener{
//             Call Load data and pass the parameter value "ZipCode"
//            This will goes to the implementation in MainActivity and call newInstance of this fragment
//            appNavigator.navigateToCurrent(zipCode)

            locationRepository.saveLocation(Location.Zipcode(zipCodeEditText.text.toString()))
            // Navigate Back
            findNavController().navigateUp()
        }

        return view
    }

}