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
import com.example.movie_catalog.App.Companion.filmApp
import com.example.movie_catalog.Constants.QTY_CARD
import com.example.movie_catalog.R
import com.example.movie_catalog.data.repositary.api.home.getKit.SelectedKit
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.entity.home.Film
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val premieresAdapter = FilmListAdapter { film -> onItemClick(film) }
    private val popularAdapter = FilmListAdapter { film -> onItemClick(film) }
    private val top250Adapter = FilmListAdapter { film -> onItemClick(film) }
    private val random1Adapter = FilmListAdapter { film -> onItemClick(film) }
    private val random2Adapter = FilmListAdapter { film -> onItemClick(film) }
    private val serialAdapter = FilmListAdapter { film -> onItemClick(film) }

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
//            processingRandomKit1(it)
//            processingRandomKit2(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        //Get list premier films
        processingPremieres()
        //Get list popular films
//        processingPopular()
        //Get list top250 films
//        processingTop250()
        //Get list top250 films
//        processingSerials()
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    private fun processingRandomKit1(selectedKit: SelectedKit) {
        binding.random1Kit.filmRecyclerHorizontal.adapter = random1Adapter

        if (selectedKit.genre1.genre != null || selectedKit.country1.country != null) {
            binding.random1Kit.kitName.text = (selectedKit.genre1.genre.toString() + " " +
                        selectedKit.country1.country.toString()).trim()
        } else binding.random1Kit.kitName.text = getText(R.string.random1)

        homeViewModel.randomKit1.onEach {
            random1Adapter.setListFilm(it)
            if (it.size > QTY_CARD) binding.random1Kit.showAll.visibility = View.VISIBLE
            else binding.random1Kit.showAll.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.premierKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    private fun processingRandomKit2(selectedKit: SelectedKit) {
        binding.random2Kit.filmRecyclerHorizontal.adapter = random2Adapter

        if (selectedKit.genre2.genre != null || selectedKit.country2.country != null) {
            binding.random2Kit.kitName.text = (selectedKit.genre2.genre.toString() + " " +
                    selectedKit.country2.country.toString()).trim()
        } else binding.random2Kit.kitName.text = getText(R.string.random2)

        homeViewModel.randomKit2.onEach {
            random2Adapter.setListFilm(it)
            if (it.size > QTY_CARD) binding.random2Kit.showAll.visibility = View.VISIBLE
            else binding.random2Kit.showAll.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.premierKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingPremieres() {
        binding.premierKit.filmRecyclerHorizontal.adapter = premieresAdapter
        binding.premierKit.kitName.text = getText(R.string.premieres)

        homeViewModel.premieres.onEach {
            premieresAdapter.setListFilm(it)
            if (it.size > QTY_CARD) binding.premierKit.showAll.visibility = View.VISIBLE
            else binding.premierKit.showAll.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.premierKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingPopular() {
        binding.popularKit.kitName.text = getText(R.string.popular)
        binding.popularKit.filmRecyclerHorizontal.adapter = popularAdapter

        homeViewModel.popularFilms.onEach {
            popularAdapter.setListFilm(it)
            binding.popularKit.showAll.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.popularKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingTop250() {
        binding.top250Kit.kitName.text = getText(R.string.top)
        binding.top250Kit.filmRecyclerHorizontal.adapter = top250Adapter

        homeViewModel.pageTop250.onEach {
            top250Adapter.setListFilm(it)
            binding.top250Kit.showAll.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.top250Kit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun processingSerials() {
        binding.top250Kit.kitName.text = getText(R.string.serials)
        binding.top250Kit.filmRecyclerHorizontal.adapter = serialAdapter

        homeViewModel.serials.onEach {
            serialAdapter.setListFilm(it)
            binding.serialKit.showAll.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.serialKit.showAll.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(film: Film) {
        setFragmentResult("requestKey", bundleOf("FILM" to film))
        filmApp = film
        findNavController().navigate(R.id.action_navigation_home_to_filmInfoFragment)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}