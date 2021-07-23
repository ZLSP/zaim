package com.zaimutest777.zaim.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zaimutest777.zaim.databinding.FragmentPrivacyBinding
import com.zaimutest777.zaim.privacy

class PrivacyFragment : Fragment() {
    private lateinit var binding: FragmentPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        binding.webViewPrivacy.loadUrl(privacy)

        return binding.root
    }

}