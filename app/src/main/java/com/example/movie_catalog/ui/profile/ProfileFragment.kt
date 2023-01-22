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
import com.example.movie_catalog.App
import com.example.movie_catalog.R
import com.example.movie_catalog.databinding.FragmentProfileBinding
import com.example.movie_catalog.databinding.IncludeListFilmBinding
import com.example.movie_catalog.entity.Collection
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.entity.Film
import com.example.movie_catalog.entity.Linker
import com.example.movie_catalog.entity.enumApp.Kit
import com.example.movie_catalog.entity.enumApp.ModeViewer
import com.example.movie_catalog.ui.recycler.SimpleAdapterAny
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
    //Creating an adapter for show viewed films from collection
    private val viewedAdapter = ListFilmAdapter(Constants.PROFILE_QTY_FILM_CARD,
        ModeViewer.PROFILE, { file -> onClickItem(file)}, { onClickCleaViewed() })
    //Creating an adapter for show films from collection bookmark
    private val bookmarkAdapter = ListFilmAdapter(Constants.PROFILE_QTY_FILM_CARD,
        ModeViewer.PROFILE, { file -> onClickItem(file)}, { kit -> onClickClearCollection(kit)})
    //Creating an adapter for collection
    private val collectionAdapter = SimpleAdapterAny ({ item -> onClickCollection(item) })

    @SuppressLint("CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_text).text = ""
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kit = Kit.COLLECTION
        //Display a list of viewed movies
        kit.nameKit = App.context.getString(R.string.viewed_kit)
        kit.displayText = App.context.getString(R.string.viewed_kit)
        processingView(binding.inclViewedFilm, viewedAdapter, viewModel.viewedFilm, kit)
        //Display a list bookmark movies
        kit.nameKit = App.context.getString(R.string.bookmark_kit)
        kit.displayText = App.context.getString(R.string.bookmark_head)
        processingView(binding.inclInterestingFilm, bookmarkAdapter, viewModel.bookmarkFilm, kit)
        //Display card view collection
        processingViewCollection()

        binding.inclCollection.tvCreate.setOnClickListener {
            //Launch a dialog box with a request for a new collection name.
            val dialogView = requireActivity().layoutInflater.inflate(R.layout.dialog_layout, null)
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
    //Output of movie lists
    private fun processingView(view: IncludeListFilmBinding, adapter: ListFilmAdapter,
                               flowFilms: StateFlow<List<Linker>>, kit: Kit){
        with(view){
            //Output of name list
            kitName.text = kit.displayText
            //Connecting adapter
            filmRecyclerHorizontal.adapter = adapter
            //Received and transferred to the recycler a list of films
            flowFilms.onEach {
                adapter.setListFilm(it)
                //Managing the display of the number of movies
                if (it.size > Constants.PROFILE_QTY_FILM_CARD-1) showAll.visibility = View.VISIBLE
                else showAll.visibility = View.INVISIBLE
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            //When you click on the number of movies, save the current movie and go to the full list
            //of similar movies
            showAll.setOnClickListener {
                viewModel.putKit(kit)
                findNavController().navigate(R.id.action_nav_profile_to_nav_kitFilms)
            }
        }
    }
    //Output list collection
    private fun processingViewCollection(){
        with(binding.inclCollection){
            //Connecting adapter recyclerview
            filmRecyclerHorizontal.adapter = collectionAdapter
            //Connecting layout manager recyclerview
            filmRecyclerHorizontal.layoutManager =
                GridLayoutManager(context,2,RecyclerView.HORIZONTAL,false)
            //Received and transferred to the recycler a list of films
            viewModel.collection.onEach {
                collectionAdapter.setList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }
    //The function tracks two events: clicking on an item to delete a collection and showing
    // the full list of movies in the collection.
    private fun onClickCollection(item: Any){ // item = Collection or Collection.name
        when (item){
            //Deleting collection
            is Collection -> {
                viewModel.deleteCollection(item)
            }
            //Showing the full list of movies in the collection.
            is String -> {
                val kit = Kit.COLLECTION
                kit.nameKit = item
                viewModel.putKit(kit)
                findNavController().navigate(R.id.action_nav_profile_to_nav_kitFilms)
            }
            else -> {}
        }
    }
    //When you click on the movie card, go to the page of the selected movie
    private fun onClickItem(film: Film) {
        viewModel.putFilm(film)
        findNavController().navigate(R.id.action_nav_profile_to_nav_filmInfo)
    }
    //When we click the last item in the list movie
    private fun onClickClearCollection(kit: Kit) {
        viewModel.clearCollection(kit)
    }
    //When we click the last item in the list movie
    private fun onClickCleaViewed() {
        viewModel.clearViewed()
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