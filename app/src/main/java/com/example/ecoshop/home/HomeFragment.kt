package com.example.ecoshop.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
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
    private  lateinit var imageRecycler: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel
        bindRecyclerViews()
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == ApiStatus.DONE) {
                viewModel.getTopProducts()
            }
        })
        return binding.root
    }

    private fun bindRecyclerViews() {
        binding.bestProductRecycler.adapter = ListProductAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                { old, new -> old.id == new.id },
                { old, new -> old == new })
        binding.popularProductRecycler.adapter = ListProductAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                { old, new -> old.id == new.id },
                { old, new -> old == new })
        binding.newProductRecycler.adapter = ListProductVerticalAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
                { old, new -> old.id == new.id },
                { old, new -> old == new })
        val imageAdapter = ImageAdapter(ListItemClickListener {
        },
                { old, new -> old.id == new.id },
                { old, new -> old == new })

        binding.imageRecyclerView.adapter = imageAdapter
        imageRecycler = binding.imageRecyclerView
    }

    override fun onStart() {
        super.onStart()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(imageRecycler)
    }
}