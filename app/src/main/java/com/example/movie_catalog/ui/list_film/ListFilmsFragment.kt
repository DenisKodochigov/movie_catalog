package com.example.movie_catalog.ui.list_film

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentListFilmsBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ListFilmAdapter
import com.example.movie_catalog.ui.list_person.ListPersonFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListFilmsFragment : Fragment() {

    companion object {
        fun newInstance() = ListPersonFragment()
    }

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListFilmsViewModel by viewModels()
    //Creating an adapter for show a list movies
    private val listAdapter = ListFilmAdapter(0, ModeViewer.FILM,
        { film -> onItemClick(film)}, {})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Connecting layout manager
        binding.filmRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        //Connecting adapter to recyclerview
        binding.filmRecyclerVertical.adapter = listAdapter
        //Received and transferred to the recycler a list of films
        viewModel.listLink.onEach {
            listAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //When you click on the movie card, go to the page of the selected movie
    private fun onItemClick(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_list_films_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}