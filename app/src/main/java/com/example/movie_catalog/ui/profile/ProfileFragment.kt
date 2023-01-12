package com.example.movie_catalog.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentProfileBinding
import com.example.movie_catalog.databinding.IncludeListFilmBinding
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.BottomAdapterAny
import com.example.movie_catalog.ui.recycler.ListFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    private val viewedAdapter = ListFilmAdapter(
        Constants.HOME_QTY_PROFILE, ModeViewer.PROFILE, { file -> onClickItem(file)},
        { kit -> onClickClearCollection(kit) })

    private val bookmarkAdapter = ListFilmAdapter(
        Constants.HOME_QTY_PROFILE, ModeViewer.PROFILE, { file -> onClickItem(file)},
        { kit -> onClickClearCollection(kit)})

    private val collectionAdapter = BottomAdapterAny {item -> onClickCollection(item) }

    @SuppressLint("CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).background =
            resources.getDrawable(R.drawable.gradient_toolbar, context?.theme)
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processingView(binding.inclViewedFilm, viewedAdapter, viewModel.viewedFilm, Kit.VIEWED)
        processingView(binding.inclInterestingFilm, bookmarkAdapter, viewModel.bookmarkFilm, Kit.BOOKMARK)
        processingViewCollection()

        binding.inclCollection.tvCreate.setOnClickListener {
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

    private fun processingViewCollection(){
        with(binding.inclCollection){
            filmRecyclerHorizontal.adapter = collectionAdapter
            filmRecyclerHorizontal.layoutManager =
                GridLayoutManager(context,2,RecyclerView.HORIZONTAL,false)
            viewModel.collection.onEach {
                collectionAdapter.setList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun processingView(view: IncludeListFilmBinding, adapter: ListFilmAdapter,
                               flowFilms: StateFlow<List<Linker>>, kit : Kit
    ){
        with(view){
            kitName.text = kit.nameKit
            filmRecyclerHorizontal.adapter = adapter
            flowFilms.onEach {
                adapter.setListFilm(it)
                if (it.size > Constants.HOME_QTY_FILMCARD-1) showAll.visibility = View.VISIBLE
                else showAll.visibility = View.INVISIBLE
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            showAll.setOnClickListener {
                viewModel.putKit(kit)
                findNavController().navigate(R.id.action_nav_profile_to_nav_kitFilms)
            }
        }
    }

    private fun onClickCollection(item: Any){
        when (item){
            is Collection -> viewModel.deleteCollection(item)
            is String -> {
                val kit = Kit.COLLECTION
                kit.nameKit = item
                viewModel.putKit(kit)
                findNavController().navigate(R.id.action_nav_profile_to_nav_kitFilms)
            }
            else -> {}
        }
    }

    private fun onClickItem(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_profile_to_nav_filmInfo)
    }

    private fun onClickClearCollection(kit: Kit) {
        viewModel.clearKit(kit)
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshView()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}