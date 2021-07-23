package com.zaimutest777.zaim.uslugi_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.zaimutest777.zaim.*
import com.zaimutest777.zaim.databinding.FragmentCardsBinding

class CardsFragment : Fragment() {
    private lateinit var binding: FragmentCardsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsBinding.inflate(inflater, container, false)
        var adapter = CardsAdapter(requireContext(), R.layout.list_cards, arrayCreditCards)
        val arrayBtnCatCards = ArrayList<AppCompatButton>().apply {
            add(binding.catCreditCards)
            add(binding.catDebetCards)
            add(binding.catRassrochka)
        }
        val arrayAdapters = ArrayList<CardsAdapter>().apply {
            add(CardsAdapter(requireContext(), R.layout.list_cards, arrayCreditCards))
            add(CardsAdapter(requireContext(), R.layout.list_cards, arrayDebetCards))
            add(CardsAdapter(requireContext(), R.layout.list_cards, arrayRassrochka))
        }
        val arrayGoldLine = ArrayList<LinearLayout>().apply {
            add(binding.lineCredit)
            add(binding.lineDebet)
            add(binding.lineRassrochka)
        }
        arrayBtnCatCards.forEachIndexed { index, btn ->
            btn.setOnClickListener {
                if (adapter != arrayAdapters[index]) {
                    adapter = arrayAdapters[index]
                    binding.listCards.adapter = adapter
                    for (i in 0 until arrayAdapters.size) {
                        if (i == index)
                            arrayGoldLine[i].visibility = VISIBLE
                        else
                            arrayGoldLine[i].visibility = GONE
                    }
                }
            }
        }
        binding.listCards.adapter = adapter
        return binding.root
    }

}