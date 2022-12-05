package com.example.movie_catalog.ui.film_info

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.App
import com.example.movie_catalog.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.data.repositary.api.film_info.FilmImageUrlDTO
import com.example.movie_catalog.data.repositary.api.film_info.PersonDTO
import com.example.movie_catalog.databinding.FragmentFilmInfoBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.filminfo.InfoFilmSeasons
import com.example.movie_catalog.entity.filminfo.Gallery
import com.example.movie_catalog.ui.film_info.recyclerView.FilmInfoGalleryAdapter
import com.example.movie_catalog.ui.film_info.recyclerView.PersonAdapter
import com.example.movie_catalog.ui.home.recyclerView.FilmListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding get() = _binding!!
    private val personAdapter = PersonAdapter({ person -> onPersonClick(person)}, sizeGird = 20, whatRole = 1)
    private val staffAdapter = PersonAdapter({person -> onPersonClick(person)}, sizeGird = 6, whatRole = 2)
    private val galleryAdapter = FilmInfoGalleryAdapter { image ->onImageClick(image) }
    private val similarAdapter = FilmListAdapter(Constants.HOME_QTY_FILMCARD) { film -> onSimilarClick(film) }
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
            if (it.filmInfoDTO != null) fillMaket(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        processingPerson()
        processingGallery()
        processingSimilar()
    }

    @SuppressLint("SetTextI18n")
    private fun processingSimilar() {
        binding.similar.similarRecycler.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL, false)
        binding.similar.similarRecycler.adapter = similarAdapter

        viewModel.similar.onEach {
            similarAdapter.setListFilm(it)
//#############################################################
            App.listFilmApp = it
//#############################################################
            if (it.size > Constants.HOME_QTY_FILMCARD-1) {
                binding.similar.tvQuantityMovies.visibility = View.VISIBLE
                binding.similar.tvQuantityMovies.text = it.size.toString()  + " >"
            } else {
                binding.gallery.tvQuantityImages.visibility = View.INVISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @SuppressLint("SetTextI18n")
    private fun processingGallery(){
        binding.gallery.imageRecycler.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL,false)
        binding.gallery.imageRecycler.adapter = galleryAdapter

        viewModel.gallery.onEach {
            val listImage = mutableListOf<FilmImageUrlDTO>()
            it.tabs.forEach { tab ->
                tab.imagesUrl?.let { imagesUrl -> listImage.addAll( imagesUrl.items) }
            }
//#############################################################
            App.galleryApp = it
            App.imageApp = listImage
//#############################################################
            if (listImage.isNotEmpty()) {
                galleryAdapter.setList(listImage)
                if (listImage.size > Constants.HOME_QTY_FILMCARD-15) { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    binding.gallery.tvQuantityImages.visibility = View.VISIBLE
                    binding.gallery.tvQuantityImages.text = listImage.size.toString() + " >"
                }else if (listImage.isEmpty()){
                    binding.gallery.tvQuantityImages.visibility = View.VISIBLE
                    binding.gallery.tvQuantityImages.text = resources.getString(R.string.not_data)
                } else {
                    binding.gallery.tvQuantityImages.visibility = View.INVISIBLE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.gallery.tvQuantityImages.setOnClickListener {
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_gallery)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processingPerson(){

        var actorList: List<PersonDTO> = emptyList()
        var staffList : List<PersonDTO> = emptyList()
        binding.person.personRecycler.layoutManager= GridLayoutManager(context,5,
                                                                RecyclerView.HORIZONTAL,false)
        binding.person.tvHeader.text = getText(R.string.header_actor)
        binding.person.personRecycler.adapter = personAdapter

        binding.staff.personRecycler.layoutManager= GridLayoutManager(context,2,
                                                                RecyclerView.HORIZONTAL,false)
        binding.staff.tvHeader.text = getText(R.string.header_staff)
        binding.staff.personRecycler.adapter = staffAdapter

        viewModel.person.onEach {
            actorList = it.filter { person -> person.professionKey == "ACTOR" }
            personAdapter.setListFilm(actorList)
            if (actorList.size > Constants.HOME_QTY_FILMCARD) {
                binding.person.tvQuantityActor.visibility = View.VISIBLE
                binding.person.tvQuantityActor.text = "${actorList.size} >"
            } else {
                binding.person.tvQuantityActor.visibility = View.INVISIBLE
            }

            staffList = it.filter { person -> person.professionKey != "ACTOR" }
            staffAdapter.setListFilm(staffList)
            if (staffList.size > Constants.HOME_QTY_FILMCARD) {
                binding.staff.tvQuantityActor.visibility = View.VISIBLE
                binding.staff.tvQuantityActor.text = "${staffList.size} >"
            } else {
                binding.staff.tvQuantityActor.visibility = View.INVISIBLE
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.person.tvQuantityActor.setOnClickListener {
            App.listPersonDTOApp = actorList
//            findNavController().navigate(R.id.action_nav_home_to_nav_list_person)
        }
        binding.staff.tvQuantityActor.setOnClickListener {
            App.listPersonDTOApp = staffList
//            findNavController().navigate(R.id.action_nav_home_to_nav_list_person)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillMaket(filmInfoSeasons: InfoFilmSeasons){
        val filmInfo = filmInfoSeasons.filmInfoDTO!!
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
            binding.posterBig.poster.background.alpha = 0
        }

//Show logotype or name russia or name original
        if (filmInfo.logoUrl == null) {
            binding.posterBig.logotype.visibility=View.INVISIBLE
            if (filmInfo.nameRu == null){
                if (filmInfo.nameOriginal == null){
//                    Log.d("KDS","nameRu=${filmInfo.nameRu}, nameOriginal=${filmInfo.nameOriginal}")
                    binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
                } else {
                    binding.posterBig.nameRuOrig.text = filmInfo.nameOriginal.toString().trim()
                }
            } else {
                binding.posterBig.nameRuOrig.text = filmInfo.nameRu.toString().trim()
            }
        } else {
            binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
            Glide.with(binding.posterBig.logotype).load(filmInfo.logoUrl).into(binding.posterBig.logotype)
        }
//Show rating and other name
        var stringForTextView = ""
        if (filmInfo.ratingKinopoisk == null) {
            if (filmInfo.ratingAwait == null){
                if (filmInfo.ratingGoodReview == null){
                    if (filmInfo.ratingImdb != null){
                        stringForTextView = filmInfo.ratingImdb.toString().trim()
                    }
                }else { stringForTextView = filmInfo.ratingGoodReview.toString().trim()}
            }else { stringForTextView = filmInfo.ratingAwait.toString().trim()}
        }else { stringForTextView = filmInfo.ratingKinopoisk.toString().trim()}

        if (filmInfo.nameOriginal != null){
            stringForTextView = stringForTextView + " " + filmInfo.nameOriginal.toString().trim()
        } else if (filmInfo.nameEn != null){
            stringForTextView = stringForTextView + " " + filmInfo.nameEn.toString().trim()
        } else if (filmInfo.nameRu != null) {
            stringForTextView = stringForTextView + " " + filmInfo.nameRu.toString().trim()
        }
        binding.posterBig.ratingName.text = stringForTextView
//Show year, genre, quantity seasons,
        //Add year
        if (filmInfo.year != null) stringForTextView = filmInfo.year.toString().trim()
        //Add genres
        filmInfo.genres?.forEach {
            stringForTextView = if (stringForTextView == "") { it.genre.toString().trim()}
            else { stringForTextView + ", " + it.genre.toString().trim()}
        }
        //Add seasons
        if (filmInfoSeasons.seasonsDTO?.total != null) {
            stringForTextView += ", seasons: " + filmInfoSeasons.seasonsDTO?.total.toString().trim()
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
//        Log.d("KDS", "description = $description")
        if (description.length > 250) binding.descriptionFilm.text = description.substring(0, 250) + "..."
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

    private fun onPersonClick(personDTO: PersonDTO) {
//        setFragmentResult("requestKey", bundleOf("PERSON" to personDTO))
        App.personDTOApp = personDTO
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_person)
    }

    private fun onImageClick(image: Gallery) {
//        setFragmentResult("requestKey", bundleOf("IMAGE" to image))
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_gallery)
    }

    private fun onSimilarClick(film: Film) {
//        setFragmentResult("requestKey", bundleOf("FILM" to film))
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_list_films)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object { fun newInstance() = FilmInfoFragment() }
}