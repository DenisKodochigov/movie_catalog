package com.example.movie_catalog.ui.home

import android.annotation.SuppressLint
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
import androidx.transition.TransitionInflater
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentHomeBinding
import com.example.movie_catalog.databinding.IncludeListFilmBinding
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ListFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    //Creating an adapter for show premieres
    private val premieresAdapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})
    //Creating an adapter for show popular
    private val popularAdapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})
    //Creating an adapter for show top 250
    private val top250Adapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})
    //Creating an adapter for show random 1
    private val random1Adapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})
    //Creating an adapter for show random 2
    private val random2Adapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})
    //Creating an adapter for show tv series
    private val serialAdapter = ListFilmAdapter(Constants.HOME_QTY_FILM_CARD, ModeViewer.FILM,
        { film -> onItemClick(film)}, { kit -> onClickAll(kit)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    @SuppressLint("CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
//        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get name random selected
        setNameRandomKit()
        //Get list random 1 films
        processingView(binding.random1Kit, random1Adapter, homeViewModel.randomKit1, Kit.RANDOM1)
        //Get list random 2 films
        processingView(binding.random2Kit, random2Adapter, homeViewModel.randomKit2, Kit.RANDOM2)
        //Get list premier films
        processingView(binding.premierKit, premieresAdapter, homeViewModel.premieres, Kit.PREMIERES)
        //Get list popular films
        processingView(binding.popularKit, popularAdapter, homeViewModel.popularFilms, Kit.POPULAR)
        //Get list top250 films
        processingView(binding.top250Kit, top250Adapter, homeViewModel.pageTop250, Kit.TOP250)
        //Get list serials films
        processingView(binding.serialKit, serialAdapter, homeViewModel.serials, Kit.SERIALS)
    }
    //Filling in the area of random collections
    @SuppressLint("SetTextI18n")
    private fun setNameRandomKit(){
        homeViewModel.namekit.onEach { item ->
            binding.random1Kit.kitName.text = item.genre1.genre.toString()
                .replaceFirstChar { it.uppercase() } + ", " + item.country1.country.toString()
            binding.random2Kit.kitName.text = item.genre2.genre.toString()
                .replaceFirstChar { it.uppercase() } + ", " + item.country2.country.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    //Filling in the area of other collections
    private fun processingView(view: IncludeListFilmBinding, adapter: ListFilmAdapter,
                               flowFilms: StateFlow<List<Linker>>, kit : Kit ) {
        with(view){
            //Set name kit
            kitName.text = context?.getString(kit.nameKit) ?: ""
            //Purpose of the adapter for recyclerview
            filmRecyclerHorizontal.adapter = adapter
            flowFilms.onEach {
                //Initializing the recyclerview
                adapter.setListFilm(it)
                //If the number of item is more than displayed in the list, then we show
                // the number and a link to display the full list.
                if (it.size > Constants.HOME_QTY_FILM_CARD-1) showAll.visibility = View.VISIBLE
                else showAll.visibility = View.INVISIBLE
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            //When you click on the number of items, we save the kit and
            // proceed to display the full list.
            showAll.setOnClickListener {
                homeViewModel.putKit(kit)
                findNavController().navigate(R.id.action_nav_home_to_nav_listFilm)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //When clicking on a list item
    private fun onItemClick(film: Film) {
        homeViewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_home_to_nav_filmInfo)
    }
    //When clicking on the last item in the list
    private fun onClickAll(kit: Kit) {
        homeViewModel.putKit(kit)
        findNavController().navigate(R.id.action_nav_home_to_nav_listFilm)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}