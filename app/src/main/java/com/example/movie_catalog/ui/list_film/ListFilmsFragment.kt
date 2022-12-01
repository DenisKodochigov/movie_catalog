package com.example.movie_catalog.ui.list_film

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.App
import com.example.movie_catalog.App.Companion.kitApp
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentListFilmsBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.ui.list_film.recyclerListFilms.ListFilmAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFilmsFragment : Fragment() {

    companion object {
        fun newInstance() = ListPersonFragment()
    }

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListPersonViewModel by viewModels()
    private val listAdapter = ListFilmAdapter{film -> onItemClick(film)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
            kitApp?.nameKit ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        binding.filmRecyclerVertical.adapter = listAdapter

        viewModel.listFilms.onEach {
            listAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(film: Film) {
        setFragmentResult("requestKey", bundleOf("FILM" to film))
        App.filmApp = film
        findNavController().navigate(R.id.action_nav_list_films_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}