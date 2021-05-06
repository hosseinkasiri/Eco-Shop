package com.example.ecoshop.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ecoshop.customViews.ImageAdapter
import com.example.ecoshop.customViews.ListProductAdapter
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.databinding.FragmentHomeBinding
import com.example.ecoshop.utils.ListItemClickListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel
        binding.bestProductRecycler.adapter = ListProductAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        binding.popularProductRecycler.adapter = ListProductAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        binding.newProductRecycler.adapter = ListProductVerticalAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        binding.imageRecyclerView.adapter = ImageAdapter(ListItemClickListener {
        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null){
                findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        return binding.root
    }
}