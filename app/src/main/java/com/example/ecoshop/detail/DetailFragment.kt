package com.example.ecoshop.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoshop.Product
import com.example.ecoshop.R
import com.example.ecoshop.customViews.ImageAdapter
import com.example.ecoshop.customViews.ListProductAdapter
import com.example.ecoshop.databinding.FragmentDetailBinding
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.utils.LinePagerIndicatorDecoration
import com.example.ecoshop.utils.ListItemClickListener

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var product: Product
    private lateinit var detailViewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel
    private lateinit var similarAdapter: ListProductAdapter
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageRecycler: RecyclerView
    private lateinit var similarRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        product = DetailFragmentArgs.fromBundle(requireArguments()).product
        detailViewModelFactory = DetailViewModelFactory(product, (activity)!!.application)
        viewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)
        binding.detailViewModel = viewModel
        imageRecycler = binding.imageRecyclerView
        similarRecycler = binding.similarProductRecycler
        setAttributes()
        handleSimilarError()
        handleRecyclerPaging()
        return binding.root
    }

    private fun handleRecyclerPaging() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(imageRecycler)
        imageRecycler.addItemDecoration(LinePagerIndicatorDecoration())
    }

    private fun setAttributes() {
        imageAdapter = ImageAdapter(ListItemClickListener {
        },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        similarAdapter = ListProductAdapter(ListItemClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailSelf(it))
        },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        viewModel.similarProducts.observe(viewLifecycleOwner, Observer {
            similarAdapter.submitList(it)
        })
        binding.reloading.setOnClickListener {
            viewModel.reLoadingDetail()
        }
        binding.buyDetailButton.setOnClickListener {
           viewModel.shopProduct()
        }
        imageRecycler.adapter = imageAdapter
        similarRecycler.adapter = similarAdapter
    }

    private fun handleSimilarError() {
        viewModel.statusDetail.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.statusImageDetail.visibility = View.VISIBLE
                    binding.statusImageDetail.setImageResource(R.drawable.loading_animation)
                    binding.reloading.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    binding.statusImageDetail.visibility = View.VISIBLE
                    binding.statusImageDetail.setImageResource(R.drawable.ic_connection_error)
                    binding.reloading.visibility = View.VISIBLE
                }
                ApiStatus.DONE -> {
                    binding.statusImageDetail.visibility = View.GONE
                    binding.reloading.visibility = View.GONE
                }
            }
        })
    }
}