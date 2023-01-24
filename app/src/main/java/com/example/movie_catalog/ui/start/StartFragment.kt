package com.example.movie_catalog.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentStartBinding
import com.example.movie_catalog.entity.ImageStart
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.images.ImagesFragment
import com.example.movie_catalog.ui.recycler.ViewerPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        //Redefining the return point for navigation
        findNavController().graph.setStartDestination(R.id.nav_home)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        //Hiding the navigation bar for the start fragment
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.INVISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Received and transferred to the recycler a list of films
        viewModel.listImage.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //Filling in the matrix with data
    private fun processingTabLayout(listImage: List<ImageStart>) {
        //Creating adapter for viewerpager2
        val adapter = ViewerPageAdapter(ModeViewer.START) {}
        //Filled in viewerpaer2 with data
        adapter.setList(listImage)
        //Connecting adapter to recyclerview
        binding.viewpager.adapter = adapter
        //Processing of clicking on the "skip" text
        binding.tvNext.setOnClickListener {
            findNavController().navigate(R.id.action_nav_start_to_nav_home)
        }
        var stateMy = 0
        //Transition from the last slide to the page house
        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == listImage.size - 1 && positionOffsetPixels == 0 && stateMy == 1){
                    findNavController().navigate(R.id.action_nav_start_to_nav_home)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                stateMy = state
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}