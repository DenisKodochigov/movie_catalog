package com.example.movie_catalog.ui.full_list_films

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
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.App.Companion.kitApp
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentListfilmsBinding
import com.example.movie_catalog.entity.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.full_list_films.recyclerViewFLF.FullListFilmAdapter
import com.example.movie_catalog.ui.full_list_films.recyclerViewFLF.FullListFilmPagingAdapter
import com.example.movie_catalog.ui.full_list_films.recyclerViewFLF.StateAdapterTopFilm
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFilmsFragment : Fragment() {

    companion object {
        fun newInstance() = ListFilmsFragment()
    }

    private var _binding: FragmentListfilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListfilmsViewModel by viewModels()
    private val listAdapter = FullListFilmAdapter{film -> onItemClick(film)}
    private val pagingAdapter = FullListFilmPagingAdapter { film -> onItemClick(film) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListfilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            kitApp?.nameKit ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmRecyclerVertical.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        if (kitApp == Kit.PREMIERES) processingPremieres()
        else processingPagingListFilm()

        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }
    }

    private fun processingPremieres(){

        binding.filmRecyclerVertical.adapter = listAdapter

        viewModel.premieres.onEach {
            listAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingPagingListFilm(){

        binding.filmRecyclerVertical.adapter = pagingAdapter.withLoadStateFooter(StateAdapterTopFilm())

        viewModel.pagedFilms.onEach {
            pagingAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        pagingAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(film: Film) {
        setFragmentResult("requestKey", bundleOf("FILM" to film))
        App.filmApp = film
        findNavController().navigate(R.id.action_listfilms_to_filmInfoFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}