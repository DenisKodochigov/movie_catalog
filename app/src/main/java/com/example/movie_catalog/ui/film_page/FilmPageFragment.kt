package com.example.movie_catalog.ui.film_page

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.R
import com.example.movie_catalog.animations.LoadImageURLShow
import com.example.movie_catalog.data.room.tables.CollectionDB
import com.example.movie_catalog.databinding.FragmentFilmPageBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Person
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FilmPageFragment : Fragment() {

    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private val personAdapter = PersonsAdapter({ person -> onPersonClick(person)}, sizeGird = 20, whatRole = 1)
    private val staffAdapter = PersonsAdapter({person -> onPersonClick(person)}, sizeGird = 6, whatRole = 2)
    private val galleryAdapter = ImagesAdapter ({ onImageClick() })
    private val similarAdapter = ListFilmAdapter(Constants.HOME_QTY_FILMCARD, ModeViewer.SIMILAR,
        { onSimilarClick()}, { kit -> onClickAll(kit)})
    private val viewModel: FilmPageViewModel by viewModels()
    private var isCollapsed = false

    @SuppressLint("CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilmPageBinding.inflate(inflater, container,false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).background =
            resources.getDrawable(R.drawable.gradient_toolbar, context?.theme)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.posterBig.viewmodel = viewModel
        binding.posterBig.lifecycleOwner = viewLifecycleOwner
        viewModel.filmInfo.onEach {
            if (it != Film()) fillPage(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        processingPerson()
        processingGallery()
        processingSimilar()
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun fillPage(filmInfo: Film){

//Show poster film. Before load image, show waiting animation.
        val animationCard = LoadImageURLShow()
        animationCard.setAnimation(binding.posterBig.poster, filmInfo.posterUrl, R.dimen.film_page_poster_radius)
//Show logotype or name russia or name original
        if (!filmInfo.logoUrl.isNullOrEmpty()) {
            Glide.with(binding.posterBig.logotype).load(filmInfo.logoUrl).into(binding.posterBig.logotype)
        } else {
            binding.posterBig.logotype.visibility=View.INVISIBLE
        }
        binding.posterBig.nameRuOrig.text = filmInfo.nameRu?.trim() ?: filmInfo.nameOriginal?.trim() ?: ""
        if ( binding.posterBig.nameRuOrig.text.isNullOrEmpty()){
            binding.posterBig.nameRuOrig.visibility=View.INVISIBLE
        }
//Show rating and other name
        var stringForTextView = ""
        if (filmInfo.rating == null) {
            if (filmInfo.ratingAwait == null){
                if (filmInfo.ratingGoodReview == null){
                    if (filmInfo.ratingImdb != null){
                        stringForTextView = filmInfo.ratingImdb.toString().trim()
                    }
                }else { stringForTextView = filmInfo.ratingGoodReview.toString().trim()}
            }else { stringForTextView = filmInfo.ratingAwait.toString().trim()}
        }else { stringForTextView = filmInfo.rating.toString().trim()}

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
        stringForTextView = if (filmInfo.year != null) filmInfo.year.toString().trim() else ""
        //Add genres
        if (stringForTextView != "") stringForTextView += ", "
        stringForTextView += filmInfo.genresTxt().trim()

        //Add seasons film_info_poster_seasons
        if (filmInfo.totalSeasons != null) {
            stringForTextView += ", " + context?.getString(R.string.film_info_poster_seasons) + filmInfo.totalSeasons.toString().trim()
        }
        binding.posterBig.yearGenreOther.text = stringForTextView
//Show short descriptions
        var description = filmInfo.shortDescription ?: ""
        description += filmInfo.description ?: ""
        //Animation description
        binding.fragmFilmInfoLinear.layoutTransition = LayoutTransition().apply {
            setDuration(600)
            enableTransitionType(LayoutTransition.CHANGING)
        }
//        Log.d("KDS", "description = $description")
        if (description.length > Constants.LENGTH_DESCRIPTION)
            binding.descriptionFilm.text = description.substring(0, Constants.LENGTH_DESCRIPTION) + "..."
        else binding.descriptionFilm.text = description
        binding.descriptionFilm.setTextAppearance(R.style.app_bold)

        binding.descriptionFilm.setOnClickListener {
            if (binding.descriptionFilm.length() > Constants.LENGTH_DESCRIPTION ) {
                if (isCollapsed) {
                    binding.descriptionFilm.setTextAppearance(R.style.app_bold)
                    binding.descriptionFilm.text =
                        description.substring(0, Constants.LENGTH_DESCRIPTION) + "..."
                    isCollapsed = false
                } else {
                    binding.descriptionFilm.setTextAppearance(R.style.app_normal)
                    binding.descriptionFilm.text = description
                    isCollapsed = true
                }
            }
        }
//Show info serial
        if (filmInfo.totalSeasons != null ){
            binding.serials.root.visibility = View.VISIBLE
//            Log.d("KDS", "Visible block serials")
            var quantitySeries = 0
            filmInfo.listSeasons?.forEach { season ->
                quantitySeries += season.episodes?.count() ?: 0
            }
            var text = "" + resources.getQuantityString(R.plurals.season, filmInfo.totalSeasons!!, filmInfo.totalSeasons!!)
            text += ", " + resources.getQuantityString(R.plurals.series, quantitySeries, quantitySeries)
            binding.serials.tvHeaderDown.text = text
        } else {
            binding.serials.root.visibility = View.INVISIBLE
            binding.serials.root.layoutParams.height = 0
//            Log.d("KDS", "Invisible block serials")
        }

// Observe clickable
        binding.serials.tvAll.setOnClickListener {
            viewModel.putFilm()
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_seasons)
        }
        binding.posterBig.ivViewed.setOnClickListener {
            viewModel.clickViewed()
        }
        binding.posterBig.ivFavorite.setOnClickListener {
            viewModel.clickFavorite()
        }
        binding.posterBig.ivBookmark.setOnClickListener {
            viewModel.clickBookmark()
        }
        binding.posterBig.ivShare.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/html"
                putExtra(Intent.EXTRA_TEXT, filmInfo.posterUrl)
            }, null)
            startActivity(share)
        }
        binding.posterBig.ivOther.setOnClickListener {
            viewModel.getCollections()
            showBottomSheetDialog(filmInfo)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processingPerson(){
        binding.person.tvHeader.text = getText(R.string.header_actor)
        binding.person.personRecycler.adapter = personAdapter
        binding.person.personRecycler.layoutManager= GridLayoutManager(context,5,
            RecyclerView.HORIZONTAL,false)
        binding.staff.tvHeader.text = getText(R.string.header_staff)
        binding.staff.personRecycler.adapter = staffAdapter
        binding.staff.personRecycler.layoutManager= GridLayoutManager(context,2,
            RecyclerView.HORIZONTAL,false)

        viewModel.person.onEach { binder ->
            val actorList = binder.filter { it.person!!.professionKey == Constants.ACTOR }
            personAdapter.setListPerson(actorList)
            if (actorList.size > Constants.INFO_QTY_PERSONCARD) {
                binding.person.tvQuantityActor.visibility = View.VISIBLE
                binding.person.tvQuantityActor.text = "${actorList.size} >"
            } else {
                binding.person.tvQuantityActor.visibility = View.INVISIBLE
            }

            val staffList = binder.filter { it.person!!.professionKey != Constants.ACTOR }
            staffAdapter.setListPerson(staffList)
            if (staffList.size > Constants.INFO_QTY_PERSONCARD) {
                binding.staff.tvQuantityActor.visibility = View.VISIBLE
                binding.staff.tvQuantityActor.text = "${staffList.size} >"
            } else {
                binding.staff.tvQuantityActor.visibility = View.INVISIBLE
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.person.tvQuantityActor.setOnClickListener {
            viewModel.putJobPerson(Constants.ACTOR)
            viewModel.putFilm()
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_list_person)
        }
        binding.staff.tvQuantityActor.setOnClickListener {
            viewModel.putJobPerson(Constants.OTHER)
            viewModel.putFilm()
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_list_person)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processingGallery(){
        binding.gallery.imageRecycler.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL,false)
        binding.gallery.imageRecycler.adapter = galleryAdapter

        viewModel.images.onEach { listImage ->

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
            viewModel.putFilm()
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_gallery)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processingSimilar() {
        binding.similar.similarRecycler.layoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL, false)
        binding.similar.similarRecycler.adapter = similarAdapter

        viewModel.similar.onEach {
            similarAdapter.setListFilm(it)
            if (it.size > Constants.HOME_QTY_FILMCARD-1) {
                binding.similar.tvQuantityMovies.visibility = View.VISIBLE
                binding.similar.tvQuantityMovies.text = it.size.toString()  + " >"
            } else {
                binding.gallery.tvQuantityImages.visibility = View.INVISIBLE
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.similar.tvQuantityMovies.setOnClickListener {
            viewModel.putFilm()
            findNavController().navigate(R.id.action_nav_filmInfo_to_nav_list_films)
        }
    }

    private fun onPersonClick(person: Person) {
        viewModel.putPerson(person)
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_person)
    }

    private fun onImageClick() {
        viewModel.putFilm()
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_gallery)
    }

    private fun onSimilarClick() {
        viewModel.putFilm()
        findNavController().navigate(R.id.action_nav_filmInfo_to_nav_list_films)
    }

    private fun onClickAll(kit: Kit) {
        viewModel.putKit(kit)
        findNavController().navigate(R.id.action_nav_home_to_nav_kitfilms)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showBottomSheetDialog(film:Film){
        val adapterBottom = BottomAdapterAny {collection -> onClickChecked(collection as CollectionDB) }
        val bottomSheetDialog = context?.let{ BottomSheetDialog(it, R.style.AppBottomSheetDialogTheme)}!!
        bottomSheetDialog.setContentView(R.layout.include_bottom_sheet)

        val animationCard = LoadImageURLShow()
        animationCard.setAnimation( bottomSheetDialog.findViewById(R.id.poster)!!,
            film.posterUrlPreview, R.dimen.gallery_list_small_card_radius)
         context?.getDrawable(R.drawable.ic_baseline_image_not_supported_24)
        bottomSheetDialog.findViewById<TextView>(R.id.bottom_name_film)?.text = film.nameRu
        bottomSheetDialog.findViewById<TextView>(R.id.bottom_genre_film)?.text = film.genresTxt()

        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_collections)
        recyclerView?.adapter = adapterBottom

        viewModel.collections.onEach {
            adapterBottom.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
//        adapterBottom.setListFilm(Plug.listCollection)
        val newCollection = bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_add_collection)
        bottomSheetDialog.show()
        newCollection?.setOnClickListener {
            //Запустить диалоговое окно, с запросом на новое название коллекции.
             val dialogView = requireActivity().layoutInflater.inflate(R.layout.dialog_layout, null)
//            val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_layout, null)
            val dialogAlert = activity?.let {
                AlertDialog.Builder(it,R.style.Style_Dialog_Rounded_Corner).setView(dialogView).create() } ?:
            throw IllegalStateException("Activity cannot be null")
            val dialogButton = dialogView.findViewById<TextView>(R.id.tv_button)
            dialogAlert.show()
            dialogButton.setOnClickListener{
                val nameCollection = dialogView.findViewById<EditText>(R.id.username).text
                viewModel.newCollection(nameCollection.toString())
                Toast.makeText(context,"New collection $nameCollection сreated", Toast.LENGTH_SHORT).show()
                dialogAlert.dismiss()
            }
        }
    }

    private fun onClickChecked(collection: CollectionDB){
        viewModel.addRemoveFilmToCollection(collection)
        Toast.makeText(context,"Selected collection: ${collection.collection?.name ?: ""}",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object { fun newInstance() = FilmPageFragment() }
}