package com.zaimutest777.zaim.uslugi_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaimutest777.zaim.CardsAdapter
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.arrayCredits
import com.zaimutest777.zaim.databinding.FragmentCreditsBinding

class CreditsFragment : Fragment() {
    private lateinit var binding: FragmentCreditsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditsBinding.inflate(inflater, container, false)
        binding.listCredits.adapter = CardsAdapter(requireContext(), R.layout.list_cards, arrayCredits)

        return binding.root
    }

}