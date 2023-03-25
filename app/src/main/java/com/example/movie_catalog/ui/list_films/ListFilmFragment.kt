package com.example.movie_catalog.ui.list_films

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
import com.example.movie_catalog.databinding.FragmentListFilmsBinding
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
class ListFilmFragment: Fragment() {

    companion object {
        fun newInstance() = ListFilmFragment()
    }

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListFilmViewModel by viewModels()

    //Creating an adapter for show premieres
    private val profileAdapter = ListFilmAdapter(0, ModeViewer.PROFILE,
        { film -> onItemClick(film)}, {onClickClearCollection()})
    //Creating an adapter for show other kit
    private val pagingAdapter = ListFilmPagingAdapter( ModeViewer.FILM) { film -> onItemClick(film) }
    //Creating an adapter for show a list movies
    private val listAdapter = ListFilmAdapter(0, ModeViewer.FILM,
        { film -> onItemClick(film)}, {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            viewModel.localKit?.displayText ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Connecting layout manager
        binding.filmRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        if (viewModel.localKit != null) {
            when (viewModel.localKit){
                Kit.PREMIERES -> processingListFilmApi()
                Kit.SIMILAR -> processingListFilmApi()
                Kit.PERSON -> processingListFilmApi()
                Kit.COLLECTION -> processingProfile()
                else -> processingPagingListFilm()
            }
        }
    }
    //Output of the full list of the selection without pagination
    private fun processingListFilmApi(){
        //Assigned an adapter
        binding.filmRecyclerVertical.adapter = listAdapter
        //Received and transferred to the recycler a list of films
        viewModel.listLink.onEach {
            listAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.swipeRefresh.isEnabled = false
    }
    //Output of the full list of the selection without pagination
    private fun processingProfile(){
        //Assigned an adapter
        binding.filmRecyclerVertical.adapter = profileAdapter
        //Received and transferred to the recycler a list of films
        viewModel.collectionFilm.onEach {
            profileAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.swipeRefresh.isEnabled = false
    }
    //Output of the full list of the selection with pagination
    private fun processingPagingListFilm(){
        //Assigned an adapter
        binding.filmRecyclerVertical.adapter = pagingAdapter.withLoadStateFooter(StateAdapterTopFilm())
        //Received and transferred to the recycler a list of films
        viewModel.pagedFilms.onEach {
            pagingAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Started displaying data loading
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }
        //Tracking data loading
        pagingAdapter.loadStateFlow.onEach {
            binding.swipeRefresh.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //When you click on the movie card, go to the page of the selected movie
    private fun onItemClick(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_listFilm_to_nav_filmInfo)
    }
    //When we click the last item in the list movie
    private fun onClickClearCollection() {
        viewModel.clearCollection()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}