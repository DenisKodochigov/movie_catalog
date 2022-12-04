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
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentGalleryBinding
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.ui.kit_films.KitFilmsFragment
import com.example.movie_catalog.ui.gallery.recycler.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = KitFilmsFragment()
    }

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            App.kitApp?.nameKit ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.galleryFlow.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(gallery: Gallery){
        binding.viewpager.adapter = ViewPagerAdapter(gallery)
        binding.viewpager.currentItem = 0
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.setCustomView(R.layout.item_gallery_tab)
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.text =
                gallery.tabs[position].nameTabDisplay
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_quantity)?.text =
                gallery.tabs[position].imagesUrl?.total.toString()
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.
            setTextColor(resources.getColor(R.color.black,  null))
        }.attach()

        binding.tabs.getTabAt(0)?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)?.
                    setBackgroundResource(R.drawable.gallery_tab_selected_rectangle)
        binding.tabs.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.
                    setTextColor(resources.getColor(R.color.white,  null))

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)?.
                            setBackgroundResource(R.drawable.gallery_tab_selected_rectangle)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.
                            setTextColor(resources.getColor(R.color.white,  null))
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)?.
                        setBackgroundResource(R.drawable.gallery_tab_unselected_rectangle)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.
                        setTextColor(resources.getColor(R.color.black,  null))
            }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
