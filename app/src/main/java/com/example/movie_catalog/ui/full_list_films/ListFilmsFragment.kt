package com.example.movie_catalog.ui.full_list_films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentListfilmsBinding
import com.example.movie_catalog.ui.home.recyclerView.FilmFullListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListFilmsFragment : Fragment() {

    companion object {fun newInstance() = ListFilmsFragment() }
    private var _binding: FragmentListfilmsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListfilmsViewModel by viewModels()
    private val premieresAdapter = FilmFullListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListfilmsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filmRecyclerVertical.layoutManager=GridLayoutManager(context,2)
        binding.filmRecyclerVertical.adapter = premieresAdapter
        viewModel.premieres.onEach {
            premieresAdapter.setListFilm(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.premieresLoading.onEach {
            if (it) binding.loading.visibility = View.VISIBLE
            else binding.loading.visibility = View.INVISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}