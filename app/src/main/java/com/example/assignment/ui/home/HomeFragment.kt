package com.example.assignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.network.model.ArticleDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), NewsRecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { NewsRecyclerAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupLayout()

    }

    private fun setupLayout() {
        recycler.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun onItemClicked(view: View, Article: Article) {

    }


}