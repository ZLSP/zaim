package com.zaimutest777.zaim.main_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.zaimutest777.zaim.*
import com.zaimutest777.zaim.databinding.FragmentStartBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception

var serverResult = false
var serverCode = 0

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        binding.btnPrivacyLink.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.privacyFragment)
        }
        binding.btnGo.setOnClickListener {
            if (binding.chechPrivacy.isChecked)
                request(it as Button)
        }
        return binding.root
    }

    private fun request(btn: Button) {
        if (testVerifacation()) {
            val url =
                    "$checkLink/?packageid=$APPLICATION_ID&usserid=$USER_ID&getz=$TIME_ZONE&getr=$GETR"
            try {
                url.httpGet().apply {
                    header("User-Agent")
                    responseString { _, respounse, result ->
                        when (result) {
                            is Result.Failure -> {
                                verificationUUID(false)
                                println(false)
                                Log.e(TAG, "Failed")
                            }
                            else -> {
                                verificationUUID(true)
                                val obj = Json.decodeFromString<CheckLink>(result.get())
                                serverResult = obj.flag.toBoolean()
                                serverCode = respounse.statusCode
                            }
                        }
                    }
                }
            } catch (e:Exception) {
                Log.e(TAG, e.message.toString())
            }
        } else
            println(false)
        Navigation.findNavController(btn).navigate(R.id.phoneFragment)
    }

    private fun verificationUUID(test: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences(
                "sharedPrefs",
                Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean("testUUID", test).apply()
    }


    private fun testVerifacation(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("testUUID", true)
    }
}