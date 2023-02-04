package com.project.androidbase

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


fun formatData(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) * (5f / 9f)
            String.format("%.2f C", temp)
        }
    }
}

fun showDialog(context: Context, tempDisplaySettingManager: MenuSettingManager) {
    AlertDialog.Builder(context)
        .setTitle("Choose Display Settings")
        .setMessage("Choose which one you like")
        .setPositiveButton("A") { _, _ ->
            tempDisplaySettingManager.updateSettings(TempDisplaySetting.Fahrenheit)
        }
        .setNegativeButton("B") { _, _ ->
            tempDisplaySettingManager.updateSettings(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener {
            Toast.makeText(context, "Setting will take place on app restart", Toast.LENGTH_SHORT)
                .show()
        }
        .show()
}
