package com.example.movie_catalog.ui.film_info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movie_catalog.databinding.FragmentFilmInfoBinding
import com.example.movie_catalog.entity.FilmInfoSeasons
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInfoBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmInfo.onEach {
            if (it.filmInfo != null) fillMaket(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    @SuppressLint("SetTextI18n")
    private fun fillMaket(filmInfoSeasons: FilmInfoSeasons){
        var genreTxt = ""
        val filmInfo = filmInfoSeasons.filmInfo!!
        binding.descriptionFilm.text =filmInfo.description
        binding.posterBig.ratingName.text = filmInfo.ratingKinopoisk.toString() + " " + filmInfo.nameRu.toString()
        filmInfo.genres?.forEach {
            genreTxt = if (genreTxt == "") { it.genre.toString()}
            else { genreTxt + ", " + it.genre.toString()}
        }
        if (filmInfo.serial!!) genreTxt += filmInfoSeasons.seasons!!.total.toString()
        binding.posterBig.yearGenreOther.text = filmInfo.year.toString() + " " + genreTxt
        if (filmInfo.logoUrl == null) {
            binding.posterBig.logotype.visibility=View.INVISIBLE
            binding.posterBig.nameRuOrig.visibility=View.VISIBLE
            binding.posterBig.nameRuOrig.text = filmInfo.nameOriginal.toString()
        }else {
            binding.posterBig.logotype.visibility=View.VISIBLE
            binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
            Glide.with(binding.posterBig.logotype).load(filmInfo.logoUrl).into(binding.posterBig.logotype)
        }
        Glide.with(binding.posterBig.poster).load(filmInfo.posterUrl).into(binding.posterBig.poster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object { fun newInstance() = FilmInfoFragment()}
}