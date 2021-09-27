package com.example.assignment.ui.home

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.Error.GenericError
import com.example.assignment.domain.model.data.Error.NetworkError
import com.example.assignment.ui.util.showToast
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), NewsRecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { NewsRecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        observeViewModel()
        setupLayout()
    }

    private fun setupLayout() {
        recycler.adapter = adapter
        button_retry.visibility = GONE
        animation.visibility = GONE
        button_retry.setOnClickListener {
            viewModel.getArticles()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            toggleLoading(it)
        }
        viewModel.articles.observe(viewLifecycleOwner) {
            showSuccess(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    NetworkError -> showErrorLayout(R.string.error_internet)
                    GenericError -> showErrorLayout(R.string.something_went_wrong)
                }
            }
        }
    }

    private fun showSuccess(it: List<Article>) {
        button_retry.visibility = GONE
        animation.visibility = GONE
        recycler.visibility = VISIBLE
        adapter.submitList(it)
    }

    private fun toggleLoading(isLoading: Boolean) {
        button_retry.visibility = GONE
        animation.visibility = GONE
        progressBar.visibility = if (isLoading) VISIBLE else GONE
    }

    private fun showErrorLayout(errorMessage: Int) {
        animation.visibility = View.VISIBLE
        animation.setAnimation(R.raw.error_dog)
        animation.playAnimation()
        showToast(errorMessage)
        recycler.visibility = GONE
        button_retry.visibility = VISIBLE
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