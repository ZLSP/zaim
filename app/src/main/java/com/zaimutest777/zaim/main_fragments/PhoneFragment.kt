package com.zaimutest777.zaim.main_fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.UslugiActivity
import com.zaimutest777.zaim.databinding.FragmentPhoneBinding
import com.zaimutest777.zaim.sheetLink
import org.json.JSONObject


class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding
    private var CODE = (1000..9999).random()
    var testPhone = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)

        binding.getCode.setOnClickListener {
                postNumber(binding.phoneNumber.text.toString())
        }
        binding.resultCode.setOnClickListener {
                verification()
                startActivity(Intent(requireContext(), UslugiActivity::class.java))
        }
        binding.phoneNumber.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                testPhone = true
                CODE = (1000..9999).random()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        return binding.root
    }
    private fun postNumber(number: String) {
        if (testPhone && testVerifacation()) {
            println("true")
            if (serverResult && serverCode == 200) {
                testPhone = false
                val bodyJson = JSONObject("{\n" +
                        "\"data\":{\n" +
                        "\"number\":$number\n" +
                        "}\n" +
                        "}")
                sheetLink.httpPost().apply {
                    jsonBody(bodyJson.toString())
                    response { request, response, result ->
                        if (response.statusCode == 201) {
                            notification(CODE)
                            binding.resultCode.isEnabled = true
                            binding.enterCode.setText(CODE.toString())
                        } else
                            binding.getCode.isEnabled = false
                    }
                }
            } else
                binding.getCode.isEnabled = false
        }

    }

    private fun notification(code: Int) {
        val NOTIFY_ID = 100
        val CHANNEL_ID = "channelId"
        val CHANNEL_NAME = "channelName"
        val notificationIntent = Intent(requireContext(), PhoneFragment::class.java)
        val contentIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Test"
            val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Код безопасности")
                    .setContentText(code.toString())

            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFY_ID, builder.build())
        } else {
            val builder = NotificationCompat.Builder(requireContext())
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Код безопасности")
                    .setContentText(code.toString())

            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFY_ID, builder.build())
        }
    }
    private fun verification() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "sharedPrefs",
            Context.MODE_PRIVATE
        )
        val insertedBoolean = true
        val editor = sharedPreferences.edit()
        editor.putBoolean("start", insertedBoolean).apply()
    }

    private fun testVerifacation(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("testUUID", true)
    }
}