package com.project.androidbase.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.androidbase.MenuSettingManager
import com.project.androidbase.R
import com.project.androidbase.formatData
import com.project.androidbase.showDialog

// ANY ACTIVITY HAS AN INTENT
class DoubleActivity : AppCompatActivity() {

    private lateinit var tempDisplaySetting: MenuSettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_second)

        tempDisplaySetting = MenuSettingManager(this)

        // Change the App bar title
        setTitle(R.string.second_activity_title)
        val secondText = findViewById<TextView>(R.id.secondActivityText)
        val secondDescript = findViewById<TextView>(R.id.secondActivityDescription)

        val text = intent.getFloatExtra("key_text", 0f)
        secondText.text = "${formatData(text, tempDisplaySetting.getSettings())} degree"
        secondDescript.text = "${intent.getStringExtra("ket_descript")}"
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
                showDialog(this, tempDisplaySetting)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}