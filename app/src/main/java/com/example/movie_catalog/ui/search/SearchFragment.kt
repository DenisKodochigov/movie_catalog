package com.example.movie_catalog.ui.search

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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentSearchBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ListFilmPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    //Creating an adapter
    private val adapter = ListFilmPagingAdapter( ModeViewer.SEARCH){ film -> onItemClick(film)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //Removing the text toolbar
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Connecting layout manager
        binding.recyclerSearch.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //Connecting adapter
        binding.recyclerSearch.adapter = adapter
        //Request for full information about the film
        viewModel.pagedFilms.onEach {
            adapter.submitData(it)
//            val textSearch = binding.etSearch.text
//            if (textSearch.isNotEmpty()) {
//                adapter.submitData(it.filter { item ->
//                    item.film?.nameEn!!.contains(textSearch) ||
//                    item.film?.nameRu!!.contains(textSearch) ||
//                    item.film?.nameOriginal!!.contains(textSearch) ||
//                    item.person?.nameEn!!.contains(textSearch) ||
//                    item.person?.nameRu!!.contains(textSearch)
//                })
//            } else {
//                adapter.submitData(it)
//            }
//            Log.d("KDS", "SearchFragment, end load list.")
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Started displaying data loading
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        //Tracking data loading
        adapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Search by the entered text
        binding.ivSearch.setOnClickListener {
            viewModel.putTextSearch(binding.etSearch.text.toString())
            adapter.refresh()
        }
        //Start page editing settings
        binding.ivConfig.setOnClickListener {
            findNavController().navigate(R.id.action_nav_search_to_nav_setting)
        }
    }
    //Action for new settings
    override fun onStart() {
        super.onStart()
        viewModel.startSearch("")
    }
    //When you click on the movie card, go to the page of the selected movie
    private fun onItemClick(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_search_to_nav_filmInfo)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}