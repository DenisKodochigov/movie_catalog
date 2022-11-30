package com.example.movie_catalog.ui.person

import androidx.lifecycle.ViewModelProvider
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
import androidx.navigation.fragment.findNavController
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.databinding.FragmentPersonBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.home.HomeViewModel
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import kotlinx.coroutines.flow.onEach

class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonViewModel by viewModels()
    private val bestfilmAdapter = FilmListAdapter { film -> onItemClick(film) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personViewModel.bestfilm.onEach {
            var listFilm = mutableListOf<Film>()
            it.films.forEach {
                listFilm.add(Film(
                    filmId = it.filmId,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn,
                    rating = it.rating,

                ))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(film: Film) {
        setFragmentResult("requestKey", bundleOf("FILM" to film))
        App.filmApp = film
        findNavController().navigate(R.id.action_navigation_home_to_filmInfoFragment)
    }
}