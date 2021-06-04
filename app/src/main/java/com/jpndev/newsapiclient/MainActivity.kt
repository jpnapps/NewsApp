package com.jpndev.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jpndev.newsapiclient.databinding.ActivityMainBinding
import com.jpndev.newsapiclient.presentation.NewsAdapter
import com.jpndev.newsapiclient.presentation.viewmodel.NewsViewModel
import com.jpndev.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var  factory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel
    lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var newsadapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController= navHostFragment.navController
        binding.btmNav.setupWithNavController(
            navController
        )
        viewModel=ViewModelProvider(this,factory).get(NewsViewModel::class.java)
    }
}