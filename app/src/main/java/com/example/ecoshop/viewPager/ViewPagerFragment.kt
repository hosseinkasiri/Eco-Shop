package com.example.ecoshop.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ecoshop.R
import com.example.ecoshop.databinding.FragmentViewPagerBinding
import com.example.ecoshop.home.HomeFragment

class ViewPagerFragment : Fragment() {

    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var binding: FragmentViewPagerBinding
    lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewPager = binding.viewPager
        fragmentList = arrayListOf(
                HomeFragment(),
                HomeFragment(),
                HomeFragment()
        )
        viewPager.isUserInputEnabled = false
        viewPagerAdapter = ViewPagerAdapter(fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle)
        viewPager.adapter = viewPagerAdapter
        setViewPagerToBottomNavigation(binding)

        return binding.root
    }

    private fun setViewPagerToBottomNavigation(binding: FragmentViewPagerBinding) {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> {
                    viewPager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collections_menu -> {
                    viewPager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.user_menu -> {
                    viewPager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}