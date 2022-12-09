package com.example.movie_catalog.ui.home

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
import com.example.movie_catalog.App.Companion.filmApp
import com.example.movie_catalog.App.Companion.kitApp
import com.example.movie_catalog.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.databinding.IncludeHomeFilmListBinding
import com.example.movie_catalog.entity.filminfo.Kit
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val premieresAdapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }
    private val popularAdapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }
    private val top250Adapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }
    private val random1Adapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }
    private val random2Adapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }
    private val serialAdapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onItemClick(film) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get data for two random kit cinema
        homeViewModel.genreMap.onEach {
            if (it.genre1.id != null) Kit.RANDOM1.genreID = it.genre1.id!!
            if (it.country1.id != null) Kit.RANDOM1.countryID = it.country1.id!!
            Kit.RANDOM1.nameKit  = if (it.genre1.genre != null || it.country1.country != null) {
                (it.genre1.genre.toString() + " " + it.country1.country.toString()).trim()
            } else getText(R.string.random1).toString()
            processingView(binding.random1Kit, random1Adapter, homeViewModel.randomKit1, Kit.RANDOM1)

            if (it.genre2.id != null) Kit.RANDOM2.genreID = it.genre2.id!!
            if (it.country2.id != null) Kit.RANDOM2.countryID = it.country2.id!!
            Kit.RANDOM2.nameKit = if (it.genre2.genre != null || it.country2.country != null) {
                (it.genre2.genre.toString() + " " + it.country2.country.toString()).trim()
            } else getText(R.string.random2).toString()
            processingView(binding.random2Kit, random2Adapter, homeViewModel.randomKit2, Kit.RANDOM2)

        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Get list premier films
        processingView(binding.premierKit, premieresAdapter, homeViewModel.premieres, Kit.PREMIERES)
        //Get list popular films
        processingView(binding.popularKit, popularAdapter, homeViewModel.popularFilms, Kit.POPULAR)
        //Get list top250 films
        processingView(binding.top250Kit, top250Adapter, homeViewModel.pageTop250, Kit.TOP250)
        //Get list top250 films
        processingView(binding.serialKit, serialAdapter, homeViewModel.serials, Kit.SERIALS)
    }

    private fun processingView(view: IncludeHomeFilmListBinding, adapter: FilmListAdapter,
                               flowFilms: StateFlow<List<Film>>, kit : Kit
    ){
        with(view){
            kitName.text = kit.nameKit
            filmRecyclerHorizontal.adapter = adapter
            flowFilms.onEach {
                adapter.setListFilm(it)
                if (it.size > Constants.HOME_QTY_FILMCARD-1) showAll.visibility = View.VISIBLE
                else showAll.visibility = View.INVISIBLE
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            showAll.setOnClickListener {
                kitApp = kit
                findNavController().navigate(R.id.action_nav_home_to_nav_kitfilms)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(film: Film) {
        setFragmentResult("requestKey", bundleOf("FILM" to film))
        filmApp = film
        findNavController().navigate(R.id.action_nav_home_to_nav_filmInfo)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}