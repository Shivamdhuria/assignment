package com.example.assignment.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.ui.util.ImageLoader
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            isElevationShadowEnabled = true
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        setUpLayout(article)
        viewModel.getComments(article.url)
        viewModel.getLikes(article.url)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.likes.observe(viewLifecycleOwner) {
            // Add plurals and localization
            textview_likes.text = "$it likes"

        }
        viewModel.comments.observe(viewLifecycleOwner) {
            //Add plurals and localization
            textview_comments.text = "$it Comments"
        }
    }

    private fun setUpLayout(article: Article) {
        article.urlToImage?.let { ImageLoader.loadImage(requireContext(), it, image_dog_detail) } ?: kotlin.run {
            ImageLoader.loadImage(requireContext(), R.drawable.placeholder, image_dog_detail)
        }
        textview_dog_breed.text = article.title
        detail_container.transitionName = article.urlToImage
        textview_content.text = article.content
    }
}