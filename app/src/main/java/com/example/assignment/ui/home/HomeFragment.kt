package com.example.assignment.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), NewsRecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { NewsRecyclerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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

    override fun onItemClicked(view: View, article: Article) {
        applyAnimation()
        val toNewsDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment(article)
        val extras = FragmentNavigatorExtras(view to article.urlToImage.orEmpty())
        navigate(toNewsDetailFragment, extras)
    }

    private fun applyAnimation() {
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }
    }

    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination, extraInfo) }
        }
}