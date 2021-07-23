package com.zaimutest777.zaim

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cards.*

class DetailsActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        Picasso.with(this)
                .load(intent.getStringExtra("imageUrl"))
                .placeholder(R.drawable.svg_placeholder_credit_card)
                .error(R.drawable.svg_placeholder_credit_card)
                .into(logoSupport)
        if (intent.getStringExtra("mastercard") != "")
            mastercardDetails.visibility = View.VISIBLE
        if (intent.getStringExtra("mir") != "")
            mirDetails.visibility = View.VISIBLE
        if (intent.getStringExtra("visa") != "")
            vizaDetails.visibility = View.VISIBLE
        if (intent.getStringExtra("qiwi") != "")
            qiwiDetails.visibility = View.VISIBLE
        if (intent.getStringExtra("yandex") != "")
            yandexDetails.visibility = View.VISIBLE
        if (intent.getStringExtra("cash") != "")
            cashDetails.visibility = View.VISIBLE
        description.text = Html.fromHtml(intent.getStringExtra("description"))

        btnOformitDetails.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
                .putExtra("url", intent.getStringExtra("url"))
            startActivity(intent)
        }
    }
}