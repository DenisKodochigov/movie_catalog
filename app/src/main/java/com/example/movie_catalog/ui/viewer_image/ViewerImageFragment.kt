package com.example.movie_catalog.ui.viewer_image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.databinding.FragmentViewerImageBinding
import com.example.movie_catalog.ui.viewer_image.recycler.ViewerViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ViewerImageFragment : Fragment() {

    companion object {
        fun newInstance() = ViewerImageFragment()
    }

    private var _binding: FragmentViewerImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewerImageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentViewerImageBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listImage.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(listImages: List<FilmImageUrlDTO>) {
        binding.viewpager.adapter = ViewerViewPagerAdapter(listImages)
        binding.viewpager.currentItem = App.galleryApp?.viewingPosition!!
//        Log.d("KDS","ViewerImageFragment.processingTabLayout currentItem=${App.galleryApp?.viewingPosition!!}")
        binding.ivButtonLeft.setOnClickListener {
            if (binding.viewpager.currentItem > 0)
                binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
        binding.ivButtonRight.setOnClickListener {
            if (binding.viewpager.currentItem < listImages.size)
                binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
