package com.example.movie_catalog.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movie_catalog.App
import com.example.movie_catalog.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentPersonBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonViewModel by viewModels()
    private val bestfilmAdapter = FilmListAdapter(Constants.PERSON_QTY_FILMCARD) { film -> onItemClick(film) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bestFilm:List<Film> = emptyList()
        with(binding.bestFilm){
            filmRecyclerHorizontal.adapter = bestfilmAdapter
            personViewModel.bestfilm.onEach {
                binding.personNameRu.text = it.nameRu
                binding.personNameEn.text = it.nameEn
                binding.personJob.text = it.profession
//Refresh list the best film
                bestFilm = it.films
                bestfilmAdapter.setListFilm(it.films)
//Show or hide icon "all films"
                if (it.films.size > Constants.PERSON_QTY_FILMCARD-1) showAll.visibility = View.VISIBLE
                else showAll.visibility = View.INVISIBLE
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            showAll.setOnClickListener {
                App.listFilmApp = bestFilm
                findNavController().navigate(R.id.action_nav_person_to_nav_list_films)
            }
            binding.personFilmography.setOnClickListener {
//                findNavController().navigate(R.id.action_nav_person_to_nav_list_films)
            }
        }
    }
    //Show info in select film
    private fun onItemClick(film: Film) { //Show info in select film
//        setFragmentResult("requestKey", bundleOf("FILM" to film))
        App.filmApp = film
        findNavController().navigate(R.id.action_nav_person_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}