package com.project.androidbase

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

//class MainActivity : AppCompatActivity(), AppNavigator {
class MainActivity : AppCompatActivity(){
    private lateinit var tempDisplaySettingManager: MenuSettingManager
    private val dataRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiate menu
        tempDisplaySettingManager = MenuSettingManager(this)

        // Get a reference to navController
        val navController = findNavController(R.id.nav_host_fragment)
        // Set up tool bar based on navigation "label"
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolBar).setupWithNavController(navController,appBarConfiguration)
//        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolBar).setTitle(R.string.app_name)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)

//         Add a fragment
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragmentContainer, LocationEntryFragment())
//            .commit()

    }

    // Display an menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle Item selection by checking id
        return when(item.itemId){
            R.id.settingMenu -> {
                showDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//     By implementing the interface to load method implementation
//    override fun navigateToCurrent(zipCode: String) {
//         Load Data and instantiate data repository
//         dataRepository.loadData()
//
//         Call the current data fragment to load data
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragmentContainer, CurrentDataFragment.newInstance(zipCode))
//            .commit()
//
//        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentDataFragment()
//        findNavController(R.id.nav_host_fragment).navigate(action)
//
//    }
//
//    override fun navigateToLocationEntry() {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragmentContainer, LocationEntryFragment())
//            .commit()
//
//        val action = CurrentDataFragmentDirections.actionCurrentDataFragmentToLocationEntryFragment()
//        findNavController(R.id.nav_host_fragment).navigate(action)
//    }
//
//    override fun navigateToSecondFragment(data: DataModel) {
//        val action = CurrentDataFragmentDirections.actionCurrentDataFragmentToSecondFragment(data.data, data.description)
//        findNavController(R.id.nav_host_fragment).navigate(action)
//    }

}