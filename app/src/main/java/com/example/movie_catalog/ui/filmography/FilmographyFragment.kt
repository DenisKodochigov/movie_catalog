package com.example.movie_catalog.ui.filmography

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
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentFilmographyBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.FilmographyTab
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.ui.filmography.recycler.FilmographyViewPagerAdapter
import com.example.movie_catalog.ui.gallery.GalleryFragment
import com.example.movie_catalog.ui.gallery.recycler.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmographyFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmographyViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle? ): View {

        _binding = FragmentFilmographyBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.person.onEach {
            processingTabLayout(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(person: Person) {
        binding.viewpager.adapter =
            FilmographyViewPagerAdapter(person) { film -> onClickViewPager(film) }
        binding.viewpager.currentItem = 0
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.setCustomView(R.layout.item_filmography_tab)
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)?.text =
                    FilmographyTab.profKey[person.tabs[position].key]
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_quantity)?.text =
                person.films.filter {
                        film -> film.professionKey == person.tabs[position].key }.size.toString()
            tab.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                ?.setTextColor(resources.getColor(R.color.black, null))
        }.attach()

        binding.tabs.getTabAt(0)?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
            ?.setBackgroundResource(R.drawable.gallery_tab_selected_rectangle)
        binding.tabs.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
            ?.setTextColor(resources.getColor(R.color.white, null))

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
                    ?.setBackgroundResource(R.drawable.gallery_tab_selected_rectangle)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                    ?.setTextColor(resources.getColor(R.color.white, null))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ConstraintLayout>(R.id.linearlayout)
                    ?.setBackgroundResource(R.drawable.gallery_tab_unselected_rectangle)
                tab?.customView?.findViewById<TextView>(R.id.tv_gallery_tab_name)
                    ?.setTextColor(resources.getColor(R.color.black, null))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun onClickViewPager(film: Film) {
        App.filmApp = film
        findNavController().navigate(R.id.action_nav_filmography_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}