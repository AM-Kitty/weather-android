package com.project.androidbase.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.project.androidbase.MenuSettingManager
import com.project.androidbase.databinding.FragmentSecondBinding
import com.project.androidbase.formatData

// Fragment is used to bind data
class SecondFragment: Fragment() {

    private lateinit var tempDisplaySetting: MenuSettingManager
    // Get arguments from nav graph
    private val args: SecondFragmentArgs by navArgs()

    // Instantiate ViewModel
    private lateinit var viewModelFactory: SecondFragmentViewModelFactory
    // Use viewModel scoping
    // automatically save and cache for the viewModel
    private val viewModel: SecondFragmentViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    // Might be null for the entire life cycle
    private var _binding: FragmentSecondBinding? = null
    // This property only valid between onCreateView and onDestroyView
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tempDisplaySetting = MenuSettingManager(requireContext())
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        viewModelFactory = SecondFragmentViewModelFactory(args)

//        Old way of using inflater to get UI references
//        val layout = inflater.inflate(R.layout.fragment_second, container, false)
//        val secondText = layout.findViewById<TextView>(R.id.secondActivityText)
//        val secondDescription = layout.findViewById<TextView>(R.id.secondActivityDescription)
//
//        return layout

        return binding.root

    }

    // called after onCreateView
    // Listen to change coming from the ViewModel and update UI based on the new state
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<SecondFragmentViewState>{ viewState ->
            // update the UI
            binding.secondActivityText.text = "${formatData(viewState.temp, tempDisplaySetting.getSettings())} degree"
            binding.secondActivityDescription.text = viewState.description
            //binding.forecastIcon = load(viewState.iconUrl)

        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)

//        viewModel.processArgs(args)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}