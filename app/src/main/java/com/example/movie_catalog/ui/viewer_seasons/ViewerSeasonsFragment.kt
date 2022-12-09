package com.example.movie_catalog.ui.viewer_seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movie_catalog.R
import com.example.movie_catalog.data.api.home.seasons.SeasonDTO
import com.example.movie_catalog.databinding.FragmentViewerSeasonsBinding
import com.example.movie_catalog.ui.viewer_seasons.recycler.ViewerSerialViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ViewerSeasonsFragment : Fragment() {

    companion object {
        fun newInstance() = ViewerSeasonsFragment()
    }

    private var _binding: FragmentViewerSeasonsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewerSeasonsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentViewerSeasonsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listSeason.onEach {
            (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
                it.infoFilm?.nameRu
            if (it.infoSeasons != null) processingTabLayout(it.infoSeasons?.items!!)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(listSeason: List<SeasonDTO>) {
        binding.viewpager.adapter = ViewerSerialViewPagerAdapter(listSeason)
//        Log.d("KDS","ViewerImageFragment.processingTabLayout currentItem=${App.galleryApp?.viewingPosition!!}")
        binding
        binding.ivButtonLeft.setOnClickListener {
            if (binding.viewpager.currentItem > 0)
                binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
        binding.ivButtonRight.setOnClickListener {
            if (binding.viewpager.currentItem < listSeason.size)
                binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
