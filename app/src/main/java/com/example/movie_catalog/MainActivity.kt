package com.example.movie_catalog

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movie_catalog.databinding.ActivityMainBinding
import com.example.movie_catalog.ui.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel: MainActivityViewModel by viewModels()
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main ) as NavHostFragment
        navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        if (viewModel.getConditionWork()) {
            navController.setGraph(R.navigation.fragment_navigation)
        } else {
            navController.setGraph(R.navigation.fragment_navigation_start)
        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() }
    }
}