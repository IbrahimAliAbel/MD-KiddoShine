package com.example.kiddoshine.ui.marketplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.databinding.FragmentMarketplaceBinding


class MarketplaceFragment : Fragment() {

    private var _binding: FragmentMarketplaceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val marketplaceViewModel =
            ViewModelProvider(this).get(MarketplaceViewModel::class.java)

        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMarketplace
        marketplaceViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}