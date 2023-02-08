package com.example.movie_catalog

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movie_catalog.databinding.ActivityMainBinding
import com.example.movie_catalog.entity.Constants
import com.example.movie_catalog.ui.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var pref: SharedPreferences
    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Synchronization of the data storage system in memory and
        // data in the database from previous use.
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        viewModel.synchronizationDataCenterAndDB()

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main ) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.fragment_navigation)
        graph.setStartDestination(R.id.nav_home)
        //Setting up a return point for the application.
        //If the application has already been launched, then go straight to the home fragment
        pref = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE)
        if (! pref.contains(Constants.FIRST_START)) {
            graph.setStartDestination(R.id.nav_start)
            val editorPreference: SharedPreferences.Editor = pref.edit()
            editorPreference.putBoolean(Constants.FIRST_START, true)
            editorPreference.apply()
        }
        navController.graph = graph

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.navView.visibility = View.VISIBLE
    }
}