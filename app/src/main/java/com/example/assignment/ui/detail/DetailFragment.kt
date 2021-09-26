package com.example.assignment.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.ui.util.ImageLoader
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.item_article.*

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private lateinit var viewModel: DetailViewModel

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
    }

    private fun setUpLayout(article: Article){
        Log.e("article", article.toString())
        article.urlToImage?.let { ImageLoader.loadImage(requireContext(), it, image_dog_detail) } ?: kotlin.run {
            ImageLoader.loadImage(requireContext(), R.drawable.placeholder, image_dog_detail)
        }
        textview_dog_breed.text = article.title
        detail_container.transitionName = article.urlToImage
        textview_content.text = article.content
    }
}