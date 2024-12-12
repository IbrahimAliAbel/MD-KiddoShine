package com.example.kiddoshine.ui.artikel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiddoshine.Adapter.ArticleAdapter
import com.example.kiddoshine.databinding.FragmentArtikelBinding


class ArtikelFragment : Fragment() {

    private lateinit var binding: FragmentArtikelBinding
    private lateinit var adapter: ArticleAdapter
    private val artikelViewModel: ArtikelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        artikelViewModel.fetchArticles()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        artikelViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            adapter.submitList(articles)
        })

        artikelViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        artikelViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->

        })
    }
}