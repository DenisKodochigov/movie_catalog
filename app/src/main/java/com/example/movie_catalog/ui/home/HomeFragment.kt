package com.example.movie_catalog.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModels()
    private val filmAdapter = FilmListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.premierKit.filmRecyclerHorizontal.adapter = filmAdapter
        //Get data for two random kit cinema
        homeViewModel.genreMap.onEach {
            //Get data for two random kit cinema
            binding.random1Kit.kitName.text = it["genre1"].toString()
            binding.random2Kit.kitName.text = it["genre2"].toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        //Get list premier films
        homeViewModel.premieres.onEach {
            filmAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}