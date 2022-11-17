package com.example.movie_catalog.ui.film_info

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentFilmInfoBinding
import com.example.movie_catalog.entity.FilmInfoSeasons
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilmInfoBinding.inflate(inflater, container,false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
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
        val filmInfo = filmInfoSeasons.filmInfo!!
//Show poster film
        if (filmInfo.posterUrl != null) {
            Glide.with(binding.posterBig.poster).load(filmInfo.posterUrl).into(binding.posterBig.poster)
        }
//Show logotype or name russia or name original
        if (filmInfo.logoUrl == null) {
            binding.posterBig.logotype.visibility=View.INVISIBLE
            if (filmInfo.nameRu == null){
                if (filmInfo.nameOriginal == null){
//                    Log.d("KDS","nameRu=${filmInfo.nameRu}, nameOriginal=${filmInfo.nameOriginal}")
                    binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
                } else {
                    binding.posterBig.nameRuOrig.text = filmInfo.nameOriginal.toString()
                }
            } else {
                binding.posterBig.nameRuOrig.text = filmInfo.nameRu.toString()
            }
        } else {
            binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
            Glide.with(binding.posterBig.logotype).load(filmInfo.logoUrl).into(binding.posterBig.logotype)
        }
//Show rating and other name
        var stringForTextView:String = ""
        if (filmInfo.ratingKinopoisk == null) {
            if (filmInfo.ratingAwait == null){
                if (filmInfo.ratingGoodReview == null){
                    if (filmInfo.ratingImdb != null){
                        stringForTextView = filmInfo.ratingImdb.toString()
                    }
                }else { stringForTextView = filmInfo.ratingGoodReview.toString()}
            }else { stringForTextView = filmInfo.ratingAwait.toString()}
        }else { stringForTextView = filmInfo.ratingKinopoisk.toString()}

        if (filmInfo.nameOriginal != null){
            stringForTextView = stringForTextView + " " + filmInfo.nameOriginal.toString()
        } else if (filmInfo.nameEn != null){
            stringForTextView = stringForTextView + " " + filmInfo.nameEn.toString()
        } else if (filmInfo.nameRu != null) {
            stringForTextView = stringForTextView + " " + filmInfo.nameRu.toString()
        }
        binding.posterBig.ratingName.text = stringForTextView
//Show year, genre, quantity seasons,
        //Add year
        if (filmInfo.year != null) stringForTextView = filmInfo.year.toString()
        //Add genres
        filmInfo.genres?.forEach {
            stringForTextView = if (stringForTextView == "") { it.genre.toString()}
            else { stringForTextView + ", " + it.genre.toString()}
        }
        //Add seasons
        if (filmInfoSeasons.seasons?.total != null) {
            stringForTextView += ", seasons: " + filmInfoSeasons.seasons?.total.toString()
        }
        binding.posterBig.yearGenreOther.text = stringForTextView
//Show short descriptions
        if (filmInfo.shortDescription != null){
            binding.shortDescriptionFilm.text = filmInfo.shortDescription.toString()
        } else if (filmInfo.slogan != null) {
            binding.shortDescriptionFilm.text = filmInfo.slogan.toString()
        } else {
//            binding.shortDescriptionFilm.visibility = View.INVISIBLE
            Log.d("KDS","shortDescriptionFilm=null")
        }
//Show full description
        if (filmInfo.description != null){
            if (binding.shortDescriptionFilm.text.length != 0) {
                binding.descriptionFilm.text = filmInfo.description.toString()
            } else{
                binding.descriptionFilm.text = filmInfo.description.substringAfter(".")
                binding.shortDescriptionFilm.text = filmInfo.description.substringBefore(".")
            }
        } else {
            binding.descriptionFilm.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object { fun newInstance() = FilmInfoFragment()}
}