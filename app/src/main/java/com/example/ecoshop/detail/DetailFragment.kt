package com.example.ecoshop.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ecoshop.Product
import com.example.ecoshop.customViews.ImageAdapter
import com.example.ecoshop.customViews.ListProductAdapter
import com.example.ecoshop.databinding.FragmentDetailBinding
import com.example.ecoshop.utils.ListItemClickListener

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var product: Product
    private lateinit var detailViewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        product = DetailFragmentArgs.fromBundle(requireArguments()).product
        detailViewModelFactory = DetailViewModelFactory(product, (activity)!!.application)
        viewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)
        binding.imageRecyclerView.adapter = ImageAdapter(ListItemClickListener {
        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        binding.detailViewModel = viewModel
        binding.similarProductRecycler.adapter = ListProductAdapter(ListItemClickListener {

        },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        return binding.root
    }
}