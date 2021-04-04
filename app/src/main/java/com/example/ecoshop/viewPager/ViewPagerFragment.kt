package com.example.ecoshop.viewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import com.example.ecoshop.R
import com.example.ecoshop.databinding.FragmentViewPagerBinding
import com.example.ecoshop.listProduct.ListProductFragment

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val fragmentList = arrayListOf<Fragment>(
                ListProductFragment(),
                ListProductFragment(),
                ListProductFragment()
        )
        val viewPagerAdapter = ViewPagerAdapter(fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        return binding.root
    }
}