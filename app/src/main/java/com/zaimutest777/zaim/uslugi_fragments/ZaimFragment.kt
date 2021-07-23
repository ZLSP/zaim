package com.zaimutest777.zaim.uslugi_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaimutest777.zaim.CardsAdapter
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.arrayLoans
import com.zaimutest777.zaim.databinding.FragmentZaimBinding

class ZaimFragment : Fragment() {
    private lateinit var binding: FragmentZaimBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZaimBinding.inflate(inflater, container, false)
        binding.listZaim.adapter = CardsAdapter(requireContext(), R.layout.list_cards, arrayLoans)

        return binding.root
    }

}