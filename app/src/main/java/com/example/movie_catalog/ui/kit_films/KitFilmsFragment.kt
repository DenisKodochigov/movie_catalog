package com.example.movie_catalog.ui.kit_films

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentKitFilmsBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ListFilmAdapter
import com.example.movie_catalog.ui.recycler.ListFilmPagingAdapter
import com.example.movie_catalog.ui.recycler.StateAdapterTopFilm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class KitFilmsFragment: Fragment() {

    companion object {
        fun newInstance() = KitFilmsFragment()
    }

    private var _binding: FragmentKitFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: KitfilmsViewModel by viewModels()
    private val listAdapter = ListFilmAdapter(0, ModeViewer.FILM,
        { film -> onItemClick(film)}, {})
    private val pagingAdapter = ListFilmPagingAdapter( ModeViewer.FILM) { film -> onItemClick(film) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKitFilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            viewModel.localKit?.nameKit ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        if (viewModel.localKit == Kit.PREMIERES || viewModel.localKit == Kit.COLLECTION) processingPremieres()
        else processingPagingListFilm()
    }

    private fun processingPremieres(){
        binding.filmRecyclerVertical.adapter = listAdapter
        viewModel.premieres.onEach {
            listAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.swipeRefresh.isEnabled = false
    }

    private fun processingPagingListFilm(){
        binding.filmRecyclerVertical.adapter = pagingAdapter.withLoadStateFooter(StateAdapterTopFilm())
        viewModel.pagedFilms.onEach {
            pagingAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }
        pagingAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_kitFilms_to_nav_filmInfo)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}