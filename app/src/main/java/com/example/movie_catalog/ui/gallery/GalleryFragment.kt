package com.example.movie_catalog.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentGalleryBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.full_list_films.ListFilmsFragment
import com.example.movie_catalog.ui.gallery.recycler.ImagesAdapter
import com.example.movie_catalog.ui.gallery.recycler.TabsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = ListFilmsFragment()
    }

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryViewModel by viewModels()
    private val tabsAdapter = TabsAdapter()
    private val imageAdapter = ImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            App.kitApp?.nameKit ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.galleryTabsRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.galleryImagesRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.galleryTabsRecycler.adapter = tabsAdapter
        binding.galleryImagesRecycler.adapter = imageAdapter

        viewModel.images.onEach {
            tabsAdapter.setList(it.tabs)
            imageAdapter.setList(it.imagesUrl)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

//    private fun onItemClick(film: Film) {
//        setFragmentResult("requestKey", bundleOf("FILM" to film))
//        App.filmApp = film
//        findNavController().navigate(R.id.action_listfilms_to_filmInfoFragment)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}