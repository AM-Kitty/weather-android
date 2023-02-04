package com.project.androidbase.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SecondFragmentViewModelFactory(private val args: SecondFragmentArgs): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SecondFragmentViewModel::class.java)){
            return SecondFragmentViewModel(args) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }

}

// Communicates between data and fragment
// Format the data
class SecondFragmentViewModel(args: SecondFragmentArgs): ViewModel() {

    private val _viewState: MutableLiveData<SecondFragmentViewState> = MutableLiveData()
    val viewState: LiveData<SecondFragmentViewState> = _viewState

    init {
        _viewState.value = SecondFragmentViewState(
            temp = args.temp,
            description = args.description
            // date = DATE_FORMAT.format()
        )

        // repository = DataRepository()
        // repository.loadCurrentData()

    }

//    fun processArgs(args: SecondFragmentArgs){
//        // If it already has value there, no need to recreate a SecondFragmentViewState instance
//        if(_viewState.value != null) return
//
//        _viewState.value = SecondFragmentViewState(
//            temp = args.temp,
//            description = args.description
//            // date = DATE_FORMAT.format()
//        )
//    }

}