package com.zaimutest777.zaim

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : Activity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        web.settings.javaScriptEnabled = true
        web.webViewClient = WebViewClient()
        web.loadUrl(intent.getStringExtra("url").toString())
    }
}