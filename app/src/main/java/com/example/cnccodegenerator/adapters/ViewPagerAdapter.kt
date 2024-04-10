package com.example.cnccodegenerator.adapters

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cnccodegenerator.fragments.home.MillingHome
import com.example.cnccodegenerator.fragments.home.TurningHome

class ViewPagerAdapter(activity : AppCompatActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            MillingHome()
        } else {
            TurningHome()
        }
    }
}