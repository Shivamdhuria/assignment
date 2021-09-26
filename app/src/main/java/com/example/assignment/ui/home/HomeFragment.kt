package com.example.assignment.ui.home

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.StateWrapper
import com.example.assignment.ui.util.showToast
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), NewsRecyclerAdapter.RecyclerViewClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { NewsRecyclerAdapter(this) }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enterTransition = MaterialFadeThrough().apply {
//            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
//        }
//    }

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
        button_retry.setOnClickListener {
            viewModel.getArticles()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StateWrapper.Loading -> showLoading()
                is StateWrapper.Success<*> -> showSuccess(it)
                is StateWrapper.NetworkError -> showErrorLayout(R.string.error_internet)
                is StateWrapper.GenericError -> showErrorLayout(R.string.something_went_wrong)
            }
        }
    }

    private fun showSuccess(it: StateWrapper.Success<*>) {
        button_retry.visibility = GONE
        progressBar.visibility = GONE
        adapter.submitList(it.value as List<Article>)
    }

    private fun showLoading() {
        toggleLoading(true)
        button_retry.visibility = GONE
    }

    private fun toggleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) VISIBLE else GONE
    }

    private fun showErrorLayout(errorMessage: Int) {
        showToast(errorMessage)
        button_retry.visibility = VISIBLE
        toggleLoading(false)
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