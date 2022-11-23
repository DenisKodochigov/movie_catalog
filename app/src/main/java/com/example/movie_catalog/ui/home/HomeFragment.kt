package com.example.movie_catalog.ui.home

import android.annotation.SuppressLint
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
import com.example.movie_catalog.App.Companion.filmDTOApp
import com.example.movie_catalog.Constants.QTY_CARD
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.data.repositary.api.home.premieres.FilmDTO
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModels()
    private val premieresAdapter = FilmListAdapter{film -> onItemClick(film)}
    private val popularAdapter = FilmListAdapter{film -> onItemClick(film)}
    private val top250Adapter = FilmListAdapter{film -> onItemClick(film)}
//    private val random1Adapter = FilmListAdapter{film -> onItemClick(film)}
//    private val random2Adapter = FilmListAdapter{film -> onItemClick(film)}
//    private val serialAdapter = FilmListAdapter{film -> onItemClick(film)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKitFilms()
        //Get data for two random kit cinema

        homeViewModel.genreMap.onEach {
            //Get data for two random kit cinema
            if (it["genre1"] != null) binding.random1Kit.kitName.text = it["genre1"].toString()
            if (it["genre2"] != null) binding.random2Kit.kitName.text = it["genre2"].toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        //Get list premier films
        processingPremieres()
        processingPopular()
        processingTop250()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingPremieres(){
        binding.premierKit.filmRecyclerHorizontal.adapter = premieresAdapter
        binding.premierKit.kitName.text=getText(R.string.premieres)
           homeViewModel.premieres.onEach {
            premieresAdapter.setListFilm(it.items)
            if (it.items.size>QTY_CARD) binding.premierKit.showAll.visibility = View.VISIBLE
            else binding.premierKit.showAll.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.premierKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingPopular()  {
        binding.popularKit.kitName.text=getText(R.string.popular)
        binding.popularKit.filmRecyclerHorizontal.adapter = popularAdapter

        homeViewModel.popularFilms.onEach {
            popularAdapter.setListTopFilm(it.films)
            binding.popularKit.showAll.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.popularKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingTop250()  {
        binding.popularKit.kitName.text=getText(R.string.top)
        binding.popularKit.filmRecyclerHorizontal.adapter = top250Adapter

        homeViewModel.pageTop250.onEach {
            popularAdapter.setListTopFilm(it.films)
            binding.popularKit.showAll.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.popularKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    private fun initKitFilms(){
        binding.top250Kit.kitName.text=getText(R.string.top )
        binding.serialKit.kitName.text=getText(R.string.serials)
        binding.random1Kit.kitName.text = getText(R.string.random1)
        binding.random2Kit.kitName.text = getText(R.string.random2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(filmDTO: FilmDTO) {
        setFragmentResult("requestKey", bundleOf("FILM" to filmDTO))
        filmDTOApp = filmDTO
        findNavController().navigate(R.id.action_navigation_home_to_filmInfoFragment)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}