package com.example.ecoshop.detailCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ecoshop.R
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.databinding.FragmentDetailCategoryBinding
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.utils.ListItemClickListener

class DetailCategoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailCategoryBinding
    private lateinit var viewModel: DetailCategoryViewModel
    private lateinit var viewModelFactory: DetailCategoryFactory
    private lateinit var productCategory: ProductCategory
    private lateinit var adapter:ListProductVerticalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailCategoryBinding.inflate(layoutInflater)
        productCategory = DetailCategoryFragmentArgs.fromBundle(requireArguments()).productCategory
        viewModelFactory = DetailCategoryFactory(productCategory, (activity)!!.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailCategoryViewModel::class.java)
        binding.viewModel = viewModel
        adapter = ListProductVerticalAdapter(ListItemClickListener {
            findNavController().navigate(DetailCategoryFragmentDirections.actionDetailCategoryToDetail(it))
        },
            {old, new -> old.id == new.id },
            {old, new ->  old == new})
        viewModel.products.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        binding.recyclerDetailCategory.adapter = adapter
        handleError()
        return binding.root
    }

    private fun handleError() {
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                ApiStatus.ERROR -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                ApiStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                }
            }
        })
    }
}