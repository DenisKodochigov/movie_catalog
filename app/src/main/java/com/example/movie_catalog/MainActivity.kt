package com.example.movie_catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movie_catalog.databinding.ActivityMainBinding
import com.example.movie_catalog.entity.filminfo.Kit
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_main ) as NavHostFragment

        navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
//        Kit.PREMIERES.nameKit = getText(R.string.premieres).toString()
//        Kit.POPULAR.nameKit = getText(R.string.popular).toString()
//        Kit.SERIALS.nameKit = getText(R.string.serials).toString()
//        Kit.TOP250.nameKit = getText(R.string.top).toString()
//        Kit.RANDOM1.nameKit = getText(R.string.random1).toString()
//        Kit.RANDOM2.nameKit = getText(R.string.random2).toString()
    }
}