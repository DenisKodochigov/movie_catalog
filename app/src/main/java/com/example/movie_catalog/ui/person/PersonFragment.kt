package com.example.movie_catalog.ui.person

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
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.databinding.FragmentPersonBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ListFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PersonFragment : Fragment() {

    companion object {
        fun newInstance() = PersonFragment()
    }

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonViewModel by viewModels()
    private val filmAdapter = ListFilmAdapter(0, ModeViewer.FILM,
        { film -> onItemClick(film)}, {})
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val animationCard = LoadImageURLShow()

        with(binding.bestFilm){
            filmRecyclerHorizontal.adapter = filmAdapter
            viewModel.linkrers.onEach { linker ->
                if (linker.isNotEmpty()){
                    if (linker[0].person != null) {
    //                Log.d("KDS", "onViewCreated getPerson, get class Person")
    //Show photo person. Before load image, show waiting animation.
                        binding.personNameRu.text = linker[0].person?.nameRu
                        binding.personNameEn.text = linker[0].person?.nameEn
                        binding.personJob.text = linker[0].person?.profession
    //Refresh list the best film
                        filmAdapter.setListFilm(linker)
                        //Show or hide icon "all films"
                        if (linker.size > Constants.PERSON_QTY_FILMCARD-1) {
                            showAll.visibility = View.VISIBLE
                        } else showAll.visibility = View.INVISIBLE
                    }
                }
                animationCard.setAnimation(binding.ivPhoto, linker[0].person?.posterUrl, R.dimen.card_people_radius)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            showAll.setOnClickListener {
                viewModel.putPerson()
                findNavController().navigate(R.id.action_nav_person_to_nav_list_films)
            }
            binding.personGotoLink.setOnClickListener {
                viewModel.putPerson()
                findNavController().navigate(R.id.action_nav_person_to_nav_filmography)
            }
        }
    }

    //Show info in select film
    private fun onItemClick(film: Film) { //Show info in select film
//        setFragmentResult("requestKey", bundleOf("FILM" to film))
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_person_to_nav_filmInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}