package com.example.movie_catalog.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentGalleryBinding
import com.example.movie_catalog.entity.Gallery
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ViewerPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        resources.getString(R.string.toolbar_gallery)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Requesting data to display the gallery
        viewModel.galleryFlow.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //Rendering the gallery page
    private fun processingTabLayout(gallery: Gallery) {
        //Declaring Adapters viewpager2
        val adapter = ViewerPageAdapter(ModeViewer.GALLERY) { onClickViewPager() }
        //Initializing the tabs list
        adapter.setList(gallery)
        binding.viewpager.adapter = adapter
        //Programmatically select the first tab.
        binding.viewpager.currentItem = 0
        //We fill in the tabs text fields
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.setCustomView(R.layout.item_tab)
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.text =
                gallery.tabs[position].first?.nameDisplay?.let { context?.getString(it) }
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_quantity)?.text =
                gallery.tabs[position].second.toString()
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                ?.setTextColor(resources.getColor(R.color.black, null))
        }.attach()
        //We draw the tabs
        binding.tabs.getTabAt(0)?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
            ?.setBackgroundResource(R.drawable.tab_selected_background)
        binding.tabs.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
            ?.setTextColor(resources.getColor(R.color.white, null))
        //Actions when activating a particular tabs
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
                    ?.setBackgroundResource(R.drawable.tab_selected_background)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                    ?.setTextColor(resources.getColor(R.color.white, null))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
                    ?.setBackgroundResource(R.drawable.tab_unselected_background)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                    ?.setTextColor(resources.getColor(R.color.black, null))
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    //When you click on a photo, we go to the page of viewing all photos in full size
    private fun onClickViewPager() {
        viewModel.putFilm()
        findNavController().navigate(R.id.action_nav_gallery_to_nav_viewer_image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
