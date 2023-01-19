package com.example.movie_catalog.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.databinding.FragmentSettingsBinding
import com.example.movie_catalog.entity.RecyclerData
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import com.example.movie_catalog.ui.home.HomeFragment
import com.example.movie_catalog.ui.recycler.SimpleAdapterAny
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingFragment: Fragment()  {
    private val viewModel: SettingsViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialog: BottomSheetDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val inflater = TransitionInflater.from(requireContext())
//        enterTransition = inflater.inflateTransition(R.transition.slide_right)
//        exitTransition = inflater.inflateTransition(R.transition.fade)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //All parameter requests are made via the Bottom Sheet dialog
        //Initializing the Bottom Sheet dialog
        bottomSheetDialog = context?.let{ BottomSheetDialog(it, R.style.AppBottomSheetDialogTheme) }!!
        //Showing the Bottom Sheetdialog in expanded form
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        // Get current values search settings (filter)
        val filter = viewModel.filter
        //Set radiogroup TYPE FILM to the previously selected value
        when (filter.typeFilm) {
            TypeFilm.ALL -> binding.rbAll.isChecked = true
            TypeFilm.FILM -> binding.rbFilm.isChecked = true
            TypeFilm.TV_SERIES -> binding.rbSerial.isChecked = true
            else -> {}
        }
        //Set radiogroup SORTING to the previously selected value
        when (filter.sorting) {
            SortingField.YEAR -> binding.rbData.isChecked = true
            SortingField.NUM_VOTE -> binding.rbPopular.isChecked = true
            SortingField.RATING -> binding.rbRating.isChecked = true
        }
        //Filling field page
        binding.tvCountryEnter.text = filter.country.country ?: ""
        binding.tvGenreEnter.text = filter.genre.genre ?: ""
        binding.tvYearEnter.text = "" + filter.year.first.toString() + " - " +
                filter.year.second.toString()
        binding.tvRatingEnter.text = "" + filter.rating.first.toString() + " - " +
                filter.rating.second.toString()
        binding.rangeSlider.setValues(filter.rating.first.toFloat(), filter.rating.second.toFloat())
        binding.switch1.isChecked = filter.viewed
        // Processing the selection in radiogroup TYPE FILM
        binding.rgTypeFilm.setOnCheckedChangeListener { _, b ->
            when (b){
                binding.rbAll.id -> viewModel.takeFilterType(TypeFilm.ALL)
                binding.rbFilm.id -> viewModel.takeFilterType(TypeFilm.FILM)
                binding.rbSerial.id -> viewModel.takeFilterType(TypeFilm.TV_SERIES)
                else -> {}
            }
        }
        // Processing the selection in radiogroup SORTING
        binding.rgSorting.setOnCheckedChangeListener { _, b ->
            when (b){
                binding.rbData.id -> viewModel.takeFilterSorting(SortingField.YEAR)
                binding.rbPopular.id -> viewModel.takeFilterSorting(SortingField.NUM_VOTE)
                binding.rbRating.id -> viewModel.takeFilterSorting(SortingField.RATING)
            }
        }
        // Processing the selection in switch VIEWED
        binding.switch1.setOnCheckedChangeListener { _, _ ->
            viewModel.takeViewed(binding.switch1.isChecked)
        }
        // Processing the selection in range slider (select diapason rating)
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}
            override fun onStopTrackingTouch(slider: RangeSlider) {
                viewModel.takeRating(Pair(binding.rangeSlider.values[0].toDouble(),
                                            binding.rangeSlider.values[1].toDouble()))
                binding.tvRatingEnter.text = "" + binding.rangeSlider.values[0].toString() + " - " +
                        binding.rangeSlider.values[1].toString()
            }
        })
        //Processing clicks on country
        binding.tvCountryEnter.setOnClickListener {
            showBottomSheetCountrySelect()
        }
        //Processing clicks on genres
        binding.tvGenreEnter.setOnClickListener {
            showBottomSheetGenreSelect()
        }
        //Processing clicks on years
        binding.tvYearEnter.setOnClickListener {
            showBottomSheetYearsSelect()
        }
    }
    //The procedure for processing the selection of a range of years
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun showBottomSheetYearsSelect(){
        //Creating an array of years
        val listYears = (1990..2023).toList()
        //Creating a list of Paris the year selected for the first list
        val recyclerData1 = listYears.zip(MutableList(listYears.size){false}) { year, _ ->
            RecyclerData( year, false)
        }
        //Creating a list of Paris the year selected for the second list
        val recyclerData2 = listYears.zip(MutableList(listYears.size){false}) { year, _ ->
            RecyclerData( year, false)
        }
        //Connect the outlet to the bottom sheet
        bottomSheetDialog.setContentView(R.layout.include_years_sellect)
        //Filling in the headers
        val diapasonYears = listYears[0].toString() + " - " + listYears.last().toString()
        bottomSheetDialog.findViewById<TextView>(R.id.tv_diapason_years1)?.text = diapasonYears
        bottomSheetDialog.findViewById<TextView>(R.id.tv_diapason_years2)?.text = diapasonYears
        //Creating adapters for the list of years
        val adapterFrom = SimpleAdapterAny ({ year -> onClickYear1(year as Int) })
        val recyclerFrom = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerFrom)!!
        //Connecting layout manager
        recyclerFrom.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        //connecting adaper
        recyclerFrom.adapter = adapterFrom
        //Transferred to the recycler a list of films
        adapterFrom.setList(recyclerData1)
        //Creating adapters for the list of years
        val adapterTo = SimpleAdapterAny ({ year -> onClickYear2(year as Int) })
        val recyclerTo = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerTo)!!
        //Connecting layout manager
        recyclerTo.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        //connecting adaper
        recyclerTo.adapter = adapterTo
        //Transferred to the recycler a list of films
        adapterTo.setList(recyclerData2)
        //Processing of pressing the end of the selection of the range of years
        bottomSheetDialog.findViewById<TextView>(R.id.tv_button)?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        //Processing of moving through the list of years to left
        bottomSheetDialog.findViewById<ImageView>(R.id.ivLeftFrom)?.setOnClickListener {
            scrollLeft(recyclerFrom)
        }
        //Processing of moving through the list of years to right
        bottomSheetDialog.findViewById<ImageView>(R.id.ivRightFrom)?.setOnClickListener {
            scrollRight(recyclerFrom, listYears.size)
        }
        //Processing of moving through the list of years to left
        bottomSheetDialog.findViewById<ImageView>(R.id.ivLeftTo)?.setOnClickListener {
            scrollLeft(recyclerTo)
        }
        //Processing of moving through the list of years to right
        bottomSheetDialog.findViewById<ImageView>(R.id.ivRightTo)?.setOnClickListener {
            scrollRight(recyclerTo, listYears.size)
        }
        bottomSheetDialog.show()
    }
    //Processing of moving through the list of years to left
    private fun scrollLeft(recycler: RecyclerView){
        val scrollToPosition = 16
        val layoutManager = recycler.layoutManager as GridLayoutManager
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        if (firstPosition > scrollToPosition) {
            recycler.smoothScrollToPosition(firstPosition - scrollToPosition )
        } else {
            recycler.smoothScrollToPosition(1 )
        }
    }
    //Processing of moving through the list of years to right
    private fun scrollRight(recycler: RecyclerView, countItem: Int){
        val scrollToPosition = 16
        val layoutManager = recycler.layoutManager as GridLayoutManager
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        if (lastPosition < countItem - scrollToPosition) {
            recycler.smoothScrollToPosition(lastPosition + scrollToPosition )
        } else {
            recycler.smoothScrollToPosition(countItem)
        }
    }
    //The procedure for processing the selection of a country
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showBottomSheetCountrySelect(){
        var listCountry: List<CountryIdDTO> = emptyList()
        //Connect the outlet to the bottom sheet
        bottomSheetDialog.setContentView(R.layout.include_text_sellect)
//        bottomSheetDialog?.findViewById<TextView>(R.id.bottom_name_film)?.text = film.nameRu
        //Creating adapters for the list of country
        val adapter = SimpleAdapterAny ({ country -> onClickCountry(country as CountryIdDTO) })
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_search)
        //Connecting layout manager
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //Creating adapter
        recyclerView?.adapter = adapter
        //Received and transferred to the recycler a list of country
        viewModel.countries.onEach {
            listCountry = it
            adapter.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        bottomSheetDialog.show()
        //Selection of countries by the entered characters
        val textSearch = bottomSheetDialog.findViewById<EditText>(R.id.et_search)
        textSearch?.addTextChangedListener {
            adapter.setList(listCountry.filter { country ->
                country.country!!.contains(textSearch.text, ignoreCase = true) })
        }
    }
    //The procedure for processing the selection of a genres
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showBottomSheetGenreSelect(){
        var listGenre: List<GenreIdDTO> = emptyList()
        //Connect the outlet to the bottom sheet
        bottomSheetDialog.setContentView(R.layout.include_text_sellect)
//        bottomSheetDialog?.findViewById<TextView>(R.id.bottom_name_film)?.text = film.nameRu
        //Creating adapters for the list of genres
        val adapter = SimpleAdapterAny ({ genre -> onClickGenre(genre as GenreIdDTO) })
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_search)
        //Connecting layout manager
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //Creating adapter
        recyclerView?.adapter = adapter
        //Received and transferred to the recycler a list of genres
        viewModel.genres.onEach {
            listGenre = it
            adapter.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        bottomSheetDialog.show()
        //Selection of genres by the entered characters
        val textSearch = bottomSheetDialog.findViewById<EditText>(R.id.et_search)
        textSearch?.addTextChangedListener {
            adapter.setList(listGenre.filter { genre ->
                genre.genre!!.contains(textSearch.text, ignoreCase = true) })
        }
    }
    //Processing country selection
    private fun onClickCountry(country: CountryIdDTO){
        binding.tvCountryEnter.text = country.country
        viewModel.takeCountry(country)
        bottomSheetDialog.cancel()
    }
    //Processing genres selection
    private fun onClickGenre(genre: GenreIdDTO){
        binding.tvGenreEnter.text = genre.genre
        viewModel.takeGenres(genre)
        bottomSheetDialog.cancel()
    }
    //Processing years selection
    @SuppressLint("SetTextI18n")
    private fun onClickYear1(year: Int){
        viewModel.takeYears(Pair(year,viewModel.filter.year.second))
        binding.tvYearEnter.text = "" + viewModel.filter.year.first.toString() + " - " +
                viewModel.filter.year.second.toString()
    }
    //Processing years selection
    @SuppressLint("SetTextI18n")
    private fun onClickYear2(year: Int){
        viewModel.takeYears(Pair(viewModel.filter.year.first,year))
        binding.tvYearEnter.text = "" + viewModel.filter.year.first.toString() + " - " +
                viewModel.filter.year.second.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}

