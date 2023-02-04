package com.project.androidbase

import android.content.Context

// Every enum has a "name" property
enum class TempDisplaySetting{
    Fahrenheit, Celsius
}

class MenuSettingManager (context: Context){
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSettings(setting: TempDisplaySetting){
        preferences.edit().putString("key_setting", setting.name).commit()
    }

    fun getSettings(): TempDisplaySetting{
        val settingValue = preferences.getString("key_setting", TempDisplaySetting.Fahrenheit.name) ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }

}