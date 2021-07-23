package com.zaimutest777.zaim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.zaimutest777.zaim.uslugi_fragments.CardsFragment
import com.zaimutest777.zaim.uslugi_fragments.CreditsFragment
import com.zaimutest777.zaim.uslugi_fragments.ZaimFragment
import kotlinx.android.synthetic.main.activity_uslugi.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

val arrayCreditCards = ArrayList<Cards>()
val arrayDebetCards = ArrayList<Cards>()
val arrayRassrochka = ArrayList<Cards>()
val arrayCredits = ArrayList<Cards>()
val arrayLoans = ArrayList<Cards>()

class UslugiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uslugi)
        val actionBar = supportActionBar
        actionBar!!.title = "Займы"
        val arrayFragment = ArrayList<Fragment>().apply {
            add(ZaimFragment())
            add(CardsFragment())
            add(CreditsFragment())
        }
        val arrayBottomBarBtns = ArrayList<AppCompatButton>().apply {
            add(catZaim)
            add(catCarts)
            add(catCredit)
        }
        arrayBottomBarBtns.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentScreen, arrayFragment[index])
                    addToBackStack(null)
                    commit()
                }
                when(index) {
                    0 -> actionBar.title = "Займы"
                    1 -> actionBar.title = "Карты"
                    2 -> actionBar.title = "Кредиты"
                }
            }
        }
        getCardsArray(arrayFragment)
    }

    private fun getCardsArray(arrayFragment: ArrayList<Fragment>) {
        try {
            cardLink.httpGet().responseString{ _, respounce, result ->
                if (respounce.statusCode == 200) {
                    val json = JSONObject(result.get())
                    jsonParseCards(json.getJSONArray("creditcards"), arrayCreditCards)
                    jsonParseCards(json.getJSONArray("debitcards"), arrayDebetCards)
                    jsonParseCards(json.getJSONArray("rasrochka"), arrayRassrochka)
                    jsonParseCards(json.getJSONArray("credits"), arrayCredits)
                    jsonParseCards(json.getJSONArray("loans"), arrayLoans)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentScreen, arrayFragment[0])
                        addToBackStack(null)
                        commit()
                    }
                }

            }
        } catch (e: Exception) {
            Log.e("logtest", e.message.toString())
        }
    }
    private fun jsonParseCards(jsonArray: JSONArray, arrayCards: ArrayList<Cards>) {
        for (i in 0 until jsonArray.length()) {
            arrayCards.add(Json.decodeFromString(jsonArray[i].toString()))
        }
    }

    override fun onBackPressed() {
        if ("ZaimFragment" !in supportFragmentManager.findFragmentById(R.id.fragmentScreen).toString())
            super.onBackPressed()
    }
}