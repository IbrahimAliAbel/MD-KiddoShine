package com.example.kiddoshine.ui.artikel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kiddoshine.databinding.FragmentArtikelBinding

class ArtikelFragment : Fragment() {

    private var _binding: FragmentArtikelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artikelViewModel =
            ViewModelProvider(this).get(ArtikelViewModel::class.java)

        _binding = FragmentArtikelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textArtikel
        artikelViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}