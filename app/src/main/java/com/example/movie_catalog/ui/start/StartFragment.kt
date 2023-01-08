package com.example.movie_catalog.ui.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentStartBinding
import com.example.movie_catalog.entity.ImageStart
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.images.ImagesFragment
import com.example.movie_catalog.ui.recycler.ViewerPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class StartFragment: Fragment() {

    companion object {
        fun newInstance() = ImagesFragment()
    }

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listImage.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(listImage: List<ImageStart>) {
        val adapter = ViewerPageAdapter(ModeViewer.START) {}
        adapter.setList(listImage)
        binding.viewpager.adapter = adapter
        binding.tvNext.setOnClickListener {
            findNavController().navigate(R.id.action_nav_start_to_nav_home)
        }

        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == listImage.size - 1 && positionOffsetPixels == 0){
                    findNavController().navigate(R.id.action_nav_start_to_nav_home)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}