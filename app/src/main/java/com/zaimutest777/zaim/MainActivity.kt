package com.zaimutest777.zaim

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val ID_ONE_SIGNAL = "000000-0000-0000-0000-000000000"
const val ID_METRICA = "000000-0000-0000-0000-000000000"
const val TAG = "Activity"
const val TAG_FB = "FireBase"
const val PRIVACY = "privatepolicy"
const val CARDS_LINK = "cards_link"
const val SHEET_LINK = "sheet_link"
const val CHECK_LINK = "check_link"
const val GETR = "utm_source=google-play&utm_medium=organic"
const val APPLICATION_ID = BuildConfig.APPLICATION_ID
const val USER_ID_NAME = "user_id"
lateinit var USER_ID: String
lateinit var TIME_ZONE: String
var checkLink = ""
var privacy = ""
var sheetLink = ""
var cardLink = ""
lateinit var navController:NavController

class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ID_ONE_SIGNAL)

        val config = YandexMetricaConfig.newConfigBuilder(ID_METRICA).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this.application)

        FirebaseApp.initializeApp(this)
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val updated = task.result
                        Log.d(TAG_FB, "Config params updated: $updated")
                        Log.d(TAG_FB, "Fetch and activate succeeded")
                        if (remoteConfig.getString(CHECK_LINK) != "") {
                            checkLink = remoteConfig.getString(CHECK_LINK)
                            privacy = remoteConfig.getString(PRIVACY)
                            sheetLink = remoteConfig.getString(SHEET_LINK)
                            cardLink = remoteConfig.getString(CARDS_LINK)
                        } else
                            Log.d(TAG_FB, "Fetch failed")
                        if (testStarting()) {
                            startActivity(Intent(this, UslugiActivity::class.java))
                        } else {
                            shadow.visibility = GONE
                            NavigationUI.setupActionBarWithNavController(this, navController)
                        }
                    } else {
                        Log.d(TAG_FB, "Fetch failed")
                    }
                }
        USER_ID = getUUID()
        TIME_ZONE = TimeZone.getDefault().id
        navController = findNavController(R.id.navHostFragment)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }

    private fun getUUID():String {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val result = sharedPreferences.getString(USER_ID_NAME, "").toString()
        return if (result == "") {
            val newUUID = UUID.randomUUID().toString()
            val editor = sharedPreferences.edit()
            editor.putString(USER_ID_NAME, newUUID).apply()
            sharedPreferences.getString(USER_ID_NAME, "").toString()
        } else
            result
    }

    private fun testStarting():Boolean {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("start", false)
    }

}
