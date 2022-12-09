package com.example.movie_catalog.ui.list_person

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
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.film_info.PersonDTO
import com.example.movie_catalog.databinding.FragmentListPersonBinding
import com.example.movie_catalog.ui.list_film.ListPersonViewModel
import com.example.movie_catalog.ui.list_person.recyclerListPerson.ListPersonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListPersonFragment : Fragment() {

    companion object {
        fun newInstance() = ListPersonFragment()
    }

    private var _binding: FragmentListPersonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListPersonViewModel by viewModels()
    private val listAdapter = ListPersonAdapter{person -> onItemClick(person)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListPersonBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.personRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        binding.personRecyclerVertical.adapter = listAdapter

        viewModel.listPerson.onEach {
            listAdapter.setListPerson(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(person: PersonDTO) {
//        setFragmentResult("requestKey", bundleOf("FILM" to film))
        App.personDTOApp = person
        findNavController().navigate(R.id.action_nav_list_films_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}