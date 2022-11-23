package com.example.movie_catalog.ui.film_info

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
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
import com.bumptech.glide.Glide
import com.example.movie_catalog.App
import com.example.movie_catalog.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentFilmInfoBinding
import com.example.movie_catalog.entity.filminfo.FilmInfoSeasons
import com.example.movie_catalog.entity.filminfo.person.Person
import com.example.movie_catalog.ui.film_info.recyclerView.PersonAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!
    private val actorAdapter = PersonAdapter({person -> onPersonClick(person)}, sizeGird = 20, whoteRole = 1)
    private val staffAdapter = PersonAdapter({person -> onPersonClick(person)}, sizeGird = 6, whoteRole = 2)
    private val viewModel: FilmInfoViewModel by viewModels()
    private var isCollapsed = false

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

        processingPerson()
        processingGallery()
    }

    private fun processingGallery(){

    }

    @SuppressLint("SetTextI18n")
    private fun processingPerson(){
        binding.actors.personRecycler.layoutManager= GridLayoutManager(context,5,
                                                                RecyclerView.HORIZONTAL,false)
        binding.actors.tvHeader.text = getText(R.string.header_actor)
        binding.actors.personRecycler.adapter = actorAdapter

        binding.staff.personRecycler.layoutManager= GridLayoutManager(context,2,
                                                                RecyclerView.HORIZONTAL,false)
        binding.staff.tvHeader.text = getText(R.string.header_staff)
        binding.staff.personRecycler.adapter = staffAdapter

        viewModel.person.onEach {
            actorAdapter.setListFilm(it.filter { person -> person.professionKey == "ACTOR" })
            var sizeList = it.filter { person -> person.professionKey == "ACTOR" }.size
            if (it.filter { person -> person.professionKey == "ACTOR" }.size> Constants.QTY_CARD) {
                binding.actors.tvQuantityActor.visibility = View.VISIBLE
                binding.actors.tvQuantityActor.text = "$sizeList >"
            } else {
                binding.actors.tvQuantityActor.visibility = View.INVISIBLE
            }

            staffAdapter.setListFilm(it.filter { person -> person.professionKey != "ACTOR" })
            sizeList = it.filter { person -> person.professionKey != "ACTOR" }.size
            if (sizeList > Constants.QTY_CARD) {
                binding.staff.tvQuantityActor.visibility = View.VISIBLE
                binding.staff.tvQuantityActor.text = "$sizeList >"
            } else {
                binding.staff.tvQuantityActor.visibility = View.INVISIBLE
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

//        binding.actors.tvQuantityActor.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_home_to_listfilms)
//        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillMaket(filmInfoSeasons: FilmInfoSeasons){
        val filmInfo = filmInfoSeasons.filmInfo!!
//Show poster film. Before load image, show waiting animation.
        val animationCard = binding.posterBig.poster.background as AnimationDrawable
        if (filmInfo.posterUrl == null) {
            animationCard.apply {
                setEnterFadeDuration(1000)
                setExitFadeDuration(1000)
                start()
            }
        }else{
            Glide.with(binding.posterBig.poster).load(filmInfo.posterUrl).into(binding.posterBig.poster)
            animationCard.stop()
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
        if (filmInfoSeasons.seasonsDTO?.total != null) {
            stringForTextView += ", seasons: " + filmInfoSeasons.seasonsDTO?.total.toString()
        }
        binding.posterBig.yearGenreOther.text = stringForTextView

//Show short descriptions

        var description = ""
        if (filmInfo.shortDescription != null) description = filmInfo.shortDescription.toString()
        if (filmInfo.description != null) description += filmInfo.description.toString()
        //Animation description
        binding.fragmFilmInfoLinear.layoutTransition = LayoutTransition().apply {
            setDuration(600)
            enableTransitionType(LayoutTransition.CHANGING)
        }
        Log.d("KDS", "description = $description")
        binding.descriptionFilm.text = description.substring(0, 250) + "..."
        binding.descriptionFilm.setTextAppearance(R.style.app_bold)

        binding.descriptionFilm.setOnClickListener {
            if (isCollapsed){
                binding.descriptionFilm.setTextAppearance(R.style.app_bold)
                binding.descriptionFilm.text = description.substring(0,250) + "..."
                isCollapsed = false
            } else {
                binding.descriptionFilm.setTextAppearance(R.style.app_normal)
                binding.descriptionFilm.text = description
                isCollapsed = true
            }
        }
    }

    private fun onPersonClick(person: Person) {
        setFragmentResult("requestKey", bundleOf("FILM" to person))
        App.personApp = person
        findNavController().navigate(R.id.action_filmInfoFragment_to_actorFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object { fun newInstance() = FilmInfoFragment()}
}