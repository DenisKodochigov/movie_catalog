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
import androidx.transition.TransitionInflater
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.home.getKit.CountryIdDTO
import com.example.movie_catalog.data.api.home.getKit.GenreIdDTO
import com.example.movie_catalog.databinding.FragmentSettingsBinding
import com.example.movie_catalog.entity.RecyclerData
import com.example.movie_catalog.entity.enumApp.SortingField
import com.example.movie_catalog.entity.enumApp.TypeFilm
import com.example.movie_catalog.ui.home.HomeFragment
import com.example.movie_catalog.ui.recycler.BottomAdapterAny
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog = context?.let{ BottomSheetDialog(it, R.style.AppBottomSheetDialogTheme) }!!

        val filter = viewModel.filter

        when (filter.typeFilm) {
            TypeFilm.ALL -> binding.rbAll.isChecked = true
            TypeFilm.FILM -> binding.rbFilm.isChecked = true
            TypeFilm.SERIALS -> binding.rbSerial.isChecked = true
            else -> {}
        }
        when (filter.sorting) {
            SortingField.YEAR -> binding.rbData.isChecked = true
            SortingField.NUM_VOTE -> binding.rbPopular.isChecked = true
            SortingField.RATING -> binding.rbRating.isChecked = true
            else -> {}
        }
        binding.tvCountryEnter.text = filter.country.country ?: ""
        binding.tvGenreEnter.text = filter.genre.genre ?: ""
        binding.tvYearEnter.text = "" + filter.year.first.toString() + " - " +
                filter.year.second.toString()
        binding.tvRatingEnter.text = "" + filter.rating.first.toString() + " - " +
                filter.rating.second.toString()
        binding.rangeSlider.setValues(filter.rating.first.toFloat(), filter.rating.second.toFloat())
        binding.switch1.isChecked = filter.viewed
// Processing clickable
        binding.rgTypeFilm.setOnCheckedChangeListener { _, b ->
            when (b){
                binding.rbAll.id -> viewModel.takeFilterType(TypeFilm.ALL)
                binding.rbFilm.id -> viewModel.takeFilterType(TypeFilm.FILM)
                binding.rbSerial.id -> viewModel.takeFilterType(TypeFilm.SERIALS)
                else -> {}
            }
        }
        binding.rgSorting.setOnCheckedChangeListener { _, b ->
            when (b){
                binding.rbData.id -> viewModel.takeFilterSorting(SortingField.YEAR)
                binding.rbPopular.id -> viewModel.takeFilterSorting(SortingField.NUM_VOTE)
                binding.rbRating.id -> viewModel.takeFilterSorting(SortingField.RATING)
            }
        }
        binding.switch1.setOnCheckedChangeListener { _, _ ->
            viewModel.takeViewed(binding.switch1.isChecked)
        }
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}
            override fun onStopTrackingTouch(slider: RangeSlider) {
                viewModel.takeRating(Pair(binding.rangeSlider.values[0].toDouble(),
                                            binding.rangeSlider.values[1].toDouble()))
                binding.tvRatingEnter.text = "" + binding.rangeSlider.values[0].toString() + " - " +
                        binding.rangeSlider.values[1].toString()
            }
        })
        binding.tvCountryEnter.setOnClickListener {
            showBottomSheetCountrySelect()
        }
        binding.tvGenreEnter.setOnClickListener {
            showBottomSheetGenreSelect()
        }
        binding.tvYearEnter.setOnClickListener {
            showBottomSheetYearsSelect()
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun showBottomSheetYearsSelect(){
        val listYears = (1990..2023).toList()
        val recyclerData1 = listYears.zip(MutableList(listYears.size){false}) { year, _ ->
            RecyclerData( year, false)
        }
        val recyclerData2 = listYears.zip(MutableList(listYears.size){false}) { year, _ ->
            RecyclerData( year, false)
        }
        bottomSheetDialog.setContentView(R.layout.include_years_sellect)

        val diapasonYears = listYears[0].toString() + " - " + listYears.last().toString()
        bottomSheetDialog.findViewById<TextView>(R.id.tv_diapason_years1)?.text = diapasonYears
        bottomSheetDialog.findViewById<TextView>(R.id.tv_diapason_years2)?.text = diapasonYears

        val adapterFrom = BottomAdapterAny { year -> onClickYear1(year as Int) }
        val recyclerFrom = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerFrom)!!
        recyclerFrom.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerFrom.adapter = adapterFrom
        adapterFrom.setList(recyclerData1)

        val adapterTo = BottomAdapterAny { year -> onClickYear2(year as Int) }
        val recyclerTo = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerTo)!!
        recyclerTo.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerTo.adapter = adapterTo
        adapterTo.setList(recyclerData2)

        bottomSheetDialog.findViewById<TextView>(R.id.tv_button)?.setOnClickListener {
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.findViewById<ImageView>(R.id.ivLeftFrom)?.setOnClickListener {
            scrollLeft(recyclerFrom)
        }
        bottomSheetDialog.findViewById<ImageView>(R.id.ivRightFrom)?.setOnClickListener {
            scrollRight(recyclerFrom, listYears.size)
        }
        bottomSheetDialog.findViewById<ImageView>(R.id.ivLeftTo)?.setOnClickListener {
            scrollLeft(recyclerTo)
        }
        bottomSheetDialog.findViewById<ImageView>(R.id.ivRightTo)?.setOnClickListener {
            scrollRight(recyclerTo, listYears.size)
        }
        bottomSheetDialog.show()
    }

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showBottomSheetCountrySelect(){

        var listCountry: List<CountryIdDTO> = emptyList()
        bottomSheetDialog.setContentView(R.layout.include_text_sellect)
//        bottomSheetDialog?.findViewById<TextView>(R.id.bottom_name_film)?.text = film.nameRu
        val adapter = BottomAdapterAny { country -> onClickCountry(country as CountryIdDTO) }
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_search)
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = adapter
        viewModel.countries.onEach {
            listCountry = it
            adapter.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        bottomSheetDialog.show()

        val textSearch = bottomSheetDialog.findViewById<EditText>(R.id.et_search)
        textSearch?.addTextChangedListener {
            adapter.setList(listCountry.filter { country ->
                country.country!!.contains(textSearch.text, ignoreCase = true) })
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showBottomSheetGenreSelect(){

        var listGenre: List<GenreIdDTO> = emptyList()
        bottomSheetDialog.setContentView(R.layout.include_text_sellect)
//        bottomSheetDialog?.findViewById<TextView>(R.id.bottom_name_film)?.text = film.nameRu
        val adapter = BottomAdapterAny { genre -> onClickGenre(genre as GenreIdDTO) }
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_search)
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = adapter
        viewModel.genres.onEach {
            listGenre = it
            adapter.setList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        bottomSheetDialog.show()

        val textSearch = bottomSheetDialog.findViewById<EditText>(R.id.et_search)
        textSearch?.addTextChangedListener {
            adapter.setList(listGenre.filter { genre ->
                genre.genre!!.contains(textSearch.text, ignoreCase = true) })
        }
    }

    private fun onClickCountry(country: CountryIdDTO){
        binding.tvCountryEnter.text = country.country
        viewModel.takeCountry(country)
        bottomSheetDialog.cancel()
    }
    private fun onClickGenre(genre: GenreIdDTO){
        binding.tvGenreEnter.text = genre.genre
        viewModel.takeGenres(genre)
        bottomSheetDialog.cancel()
    }
    @SuppressLint("SetTextI18n")
    private fun onClickYear1(year: Int){
        viewModel.takeYears(Pair(year,viewModel.filter.year.second))
        binding.tvYearEnter.text = "" + viewModel.filter.year.first.toString() + " - " +
                viewModel.filter.year.second.toString()
    }
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

