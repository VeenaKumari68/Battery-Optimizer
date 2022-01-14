package com.app.sharejourny.Utils

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {

    val prefs: SharedPreferences = context.getSharedPreferences("BatteryOptimizer", 0);

    var isLogin
        get() = prefs.getBoolean("isLogin",false)
        set(value) =  prefs.edit().putBoolean("isLogin",value).apply()

    var batteryLevel
        get() = prefs.getString("batteryLevel","")
        set(value) =  prefs.edit().putString("batteryLevel",value).apply()

    var temperature
        get() = prefs.getString("temperature","")
        set(value) =  prefs.edit().putString("temperature",value).apply()

    var voltage
        get() = prefs.getString("voltage","")
        set(value) =  prefs.edit().putString("voltage",value).apply()

    var health
        get() = prefs.getString("health","")
        set(value) =  prefs.edit().putString("health",value).apply()

    var userId
        get() = prefs.getString("id","")
        set(value) =  prefs.edit().putString("id",value).apply()


}