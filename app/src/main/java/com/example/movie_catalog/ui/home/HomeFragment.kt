package com.example.movie_catalog.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movie_catalog.Constants.QTY_CARD
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.ui.home.recyclerView.FilmFullListAdapter
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModels()
    private val premieresAdapter = FilmListAdapter()
//    private val popularAdapter = FilmListAdapter()
//    private val random1Adapter = FilmListAdapter()
//    private val random2Adapter = FilmListAdapter()
//    private val topAdapter = FilmListAdapter()
//    private val serialAdapter = FilmListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        initKitFilms()
        //Get data for two random kit cinema
        homeViewModel.genreMap.onEach {
            //Get data for two random kit cinema
            binding.random1Kit.kitName.text = it["genre1"].toString()
            binding.random2Kit.kitName.text = it["genre2"].toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Get list premier films
        homeViewModel.premieres.onEach {
            premieresAdapter.setListFilm(it.items)
            if (it.items.size>QTY_CARD) binding.premierKit.showAll.visibility = View.VISIBLE
            else binding.premierKit.showAll.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        processingSetOnClic()
    }

    private fun initAdapters(){
        binding.premierKit.filmRecyclerHorizontal.adapter = premieresAdapter
//        binding.popularKit.filmRecyclerHorizontal.adapter = popularAdapter
//        binding.random1Kit.filmRecyclerHorizontal.adapter = random1Adapter
//        binding.random2Kit.filmRecyclerHorizontal.adapter = random2Adapter
//        binding.top250Kit.filmRecyclerHorizontal.adapter = topAdapter
//        binding.serialKit.filmRecyclerHorizontal.adapter = serialAdapter
    }

    private fun initKitFilms(){
        binding.premierKit.kitName.text=getText(R.string.premieres)
        binding.popularKit.kitName.text=getText(R.string.popular)
        binding.top250Kit.kitName.text=getText(R.string.top )
        binding.serialKit.kitName.text=getText(R.string.serials)
    }

    fun processingSetOnClic(){
        binding.premierKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}