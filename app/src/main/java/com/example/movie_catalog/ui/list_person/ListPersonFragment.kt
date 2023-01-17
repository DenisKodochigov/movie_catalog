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
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentListPersonBinding
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.ui.recycler.PersonsAdapter
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
    //Creating an adapter for list person
    private val listAdapter = PersonsAdapter({person -> onItemClick(person)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListPersonBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Connecting layout manager to recyclerview
        binding.personRecyclerVertical.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        //Connecting adapter to recyclerview
        binding.personRecyclerVertical.adapter = listAdapter
        //Received and transferred to the recycler a list of persons
        viewModel.listPerson.onEach {
            listAdapter.setListPerson(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //When you clicked on the person's card
    private fun onItemClick(person: Person) {
        //Saved the selected perosona
        viewModel.putPersonId(person)
        findNavController().navigate(R.id.action_nav_list_person_to_nav_person)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}