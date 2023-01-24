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
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val inflater = TransitionInflater.from(requireContext())
//        enterTransition = inflater.inflateTransition(R.transition.slide_right)
//        exitTransition = inflater.inflateTransition(R.transition.fade)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Received and transferred to the recycler a list of films
        viewModel.listSeason.onEach { itFilm ->
            //Settings text toolbar
            (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text =
                itFilm.nameRu
            itFilm.listSeasons?.let { processingTabLayout(itFilm) }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun processingTabLayout(film: Film) {
        val adapter = ViewerPageAdapter(ModeViewer.SEASON) {}
        adapter.setList(film)
        //Connecting adapter
        binding.viewpager.adapter = adapter
        //Reaction to swiping to the left
        binding.ivButtonLeft.setOnClickListener {
            if (binding.viewpager.currentItem > 0)
                binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
        //Reaction to swiping to the right
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