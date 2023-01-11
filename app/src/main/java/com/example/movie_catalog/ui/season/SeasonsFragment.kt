package com.example.movie_catalog.ui.season

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentSeasonsBinding
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.ViewerPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SeasonsFragment : Fragment() {

    companion object {
        fun newInstance() = SeasonsFragment()
    }

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SeasonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listSeason.onEach {
            (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
                it.nameRu
            it.listSeasons?.let { listSeasons -> processingTabLayout(it) }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(film: Film) {
        val adapter = ViewerPageAdapter(ModeViewer.SEASON) {}
        adapter.setList(film)
        binding.viewpager.adapter = adapter
//        Log.d("KDS","ViewerImageFragment.processingTabLayout currentItem=${App.galleryApp?.viewingPosition!!}")
        binding.ivButtonLeft.setOnClickListener {
            if (binding.viewpager.currentItem > 0)
                binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
        binding.ivButtonRight.setOnClickListener {
            if (binding.viewpager.currentItem < film.listSeasons!!.size)
                binding.viewpager.currentItem = binding.viewpager.currentItem + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}