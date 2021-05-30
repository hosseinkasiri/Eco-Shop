package com.example.ecoshop.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.ecoshop.R
import com.example.ecoshop.customViews.ListProductAdapter
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.customViews.ProductBannerAdapter
import com.example.ecoshop.databinding.FragmentHomeBinding
import com.example.ecoshop.navigator.CustomNavigator
import com.example.ecoshop.utils.ListItemClickListener

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var binding: FragmentHomeBinding
    private  lateinit var imageRecycler: RecyclerView
    private lateinit var bannerAdapter: ProductBannerAdapter
    private  var handler: Handler? = null
    private lateinit var runnable: Runnable
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.homeViewModel = viewModel
        bindRecyclerViews()
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(HomeFragmentDirections.
                actionHomeFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == ApiStatus.DONE) {
                viewModel.getTopProducts()
                autoScroll()
            }
        })
        return binding.root
    }

    private fun bindRecyclerViews() {
        imageRecycler = binding.imageRecyclerView
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
        bannerAdapter = ProductBannerAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        imageRecycler.adapter = bannerAdapter
    }

    private fun autoScroll(){
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (position != 4) {
                position += 1
                imageRecycler.smoothScrollToPosition(position)
            }
            else {
                position = 0
                imageRecycler.smoothScrollToPosition(position)
            }
            handler?.postDelayed(runnable, 5000)
        }
        handler?.postDelayed(runnable, 5000)
    }

    override fun onStart() {
        super.onStart()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(imageRecycler)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
    }
}