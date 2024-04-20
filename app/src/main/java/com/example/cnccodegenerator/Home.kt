package com.example.cnccodegenerator

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cnccodegenerator.databinding.ActivityHomeBinding
import java.io.File
import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cnccodegenerator.adapters.ViewPagerAdapter
import com.example.cnccodegenerator.models.JsonFile
import com.example.cnccodegenerator.recycler_view_adapters.SketcherJsonFilesAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val TAG = "koka kola"
class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager  : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = binding.tabLayout
        setSupportActionBar(findViewById(R.id.toolbar))
        viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy{
            tab, i ->
            when (i) {
                0 -> {tab.text = "Milling"}
                1 -> {tab.text = "Turning"}
            }
        }).attach()
   }

}