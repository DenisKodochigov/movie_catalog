package com.example.movie_catalog.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentSearchBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
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
    private val adapter = ListFilmPagingAdapter( ModeViewer.FILMOGRAPHY){ film -> onItemClick(film)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var listFilm: PagingData<Linker>? = null
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearch.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerSearch.adapter = adapter
        viewModel.pagedFilms.onEach {
            listFilm = it
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        adapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.ivSearch.setOnClickListener {
            viewModel.startSearch()
        }
        binding.ivConfig.setOnClickListener {
            findNavController().navigate(R.id.action_nav_search_to_nav_setting)
        }
        binding.etSearch.addTextChangedListener {
//            adapter.submitData(
//                listFilm.filter {
//
//                }
//            )
        }
    }

    private fun onItemClick(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_search_to_nav_filmInfo)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}