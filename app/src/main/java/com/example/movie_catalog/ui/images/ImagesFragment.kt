package com.example.movie_catalog.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentImagesBinding
import com.example.movie_catalog.entity.Gallery
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ViewerPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ImagesFragment : Fragment() {

    companion object {
        fun newInstance() = ImagesFragment()
    }

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listImage.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(gallery: Gallery) {
        val adapter = ViewerPageAdapter(ModeViewer.IMAGE) {}
        adapter.setList(gallery)
        binding.viewpager.adapter = adapter
//        binding.viewpager.currentItem = viewModel.takeGallery()?.viewingPosition!!
        binding.ivButtonLeft.setOnClickListener {
            if (binding.viewpager.currentItem > 0)
                binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
        binding.ivButtonRight.setOnClickListener {
            if (binding.viewpager.currentItem < gallery.images.size)
                binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
