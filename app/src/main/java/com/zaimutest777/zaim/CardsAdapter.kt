package com.zaimutest777.zaim

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso

class CardsAdapter(ctx: Context, private val res: Int, private val item: ArrayList<Cards>)
    : ArrayAdapter<Cards>(ctx, res, item) {
    private val inflater = LayoutInflater.from(ctx)
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = inflater.inflate(res, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.apply {
            title.text = item[position].title
            Picasso.with(context)
                .load(item[position].imageUrl)
                .placeholder(R.drawable.svg_placeholder_credit_card)
                .error(R.drawable.svg_placeholder_credit_card)
                .into(logo)
            summText.text = item[position].summText
            betText.text = item[position].betText
            if (item[position].mastercard != "")
                mastercard.visibility = VISIBLE
            if (item[position].mir != "")
                mir.visibility = VISIBLE
            if (item[position].visa != "")
                viza.visibility = VISIBLE
            if (item[position].qiwi != "")
                qiwi.visibility = VISIBLE
            if (item[position].yandex != "")
                yandex.visibility = VISIBLE
            if (item[position].cash != "")
                cash.visibility = VISIBLE
            val arrayStars = ArrayList<ImageView>().apply {
                add(star1)
                add(star2)
                add(star3)
                add(star4)
                add(star5)
            }
            for (i in 0 until item[position].score.toInt()-1){
                arrayStars[i].visibility = VISIBLE
            }

            support.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java).apply {
                    putExtra("imageUrl", item[position].imageUrl)
                    putExtra("mastercard", item[position].mastercard)
                    putExtra("mir", item[position].mir)
                    putExtra("visa", item[position].visa)
                    putExtra("qiwi", item[position].qiwi)
                    putExtra("yandex", item[position].yandex)
                    putExtra("cash", item[position].cash)
                    putExtra("description", item[position].description)
                    putExtra("url", item[position].url)
                }
                context.startActivity(intent)
            }
            btnOformit.setOnClickListener {
                val intent = Intent(context, WebActivity::class.java)
                        .putExtra("url", item[position].url)
                context.startActivity(intent)
            }
        }

        return view
    }

}
    private class ViewHolder constructor(view: View) {
        val title = view.findViewById<TextView>(R.id.title)!!
        val logo = view.findViewById<ImageView>(R.id.logo)!!
        val summText = view.findViewById<TextView>(R.id.summText)!!
        val betText = view.findViewById<TextView>(R.id.betText)!!
        val mastercard = view.findViewById<ImageView>(R.id.mastercard)!!
        val mir = view.findViewById<ImageView>(R.id.mir)!!
        val viza = view.findViewById<ImageView>(R.id.viza)!!
        val qiwi = view.findViewById<ImageView>(R.id.qiwi)!!
        val yandex = view.findViewById<ImageView>(R.id.yandex)!!
        val cash = view.findViewById<ImageView>(R.id.cash)!!
        val star1 = view.findViewById<ImageView>(R.id.star1)!!
        val star2 = view.findViewById<ImageView>(R.id.star2)!!
        val star3 = view.findViewById<ImageView>(R.id.star3)!!
        val star4 = view.findViewById<ImageView>(R.id.star4)!!
        val star5 = view.findViewById<ImageView>(R.id.star5)!!
        val support = view.findViewById<TextView>(R.id.support)!!
        val btnOformit = view.findViewById<TextView>(R.id.btnOformit)!!
}