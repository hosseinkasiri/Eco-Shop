package com.example.ecoshop.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ecoshop.customViews.CategoryAdapter
import com.example.ecoshop.databinding.FragmentCategoriesBinding
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.utils.ListItemClickListener

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: CategoriesViewModel by lazy {
        ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.categoriesRecycler.adapter = CategoryAdapter(ListItemClickListener { productCategory ->
            findNavController().navigate(CategoriesFragmentDirections.
            actionCategoriesFragmentToDetailCategoryFragment(productCategory))
        },
            {old, new -> old.id == new.id },
            {old, new ->  old == new})
        viewModel.status.observe(viewLifecycleOwner, Observer { apiStatus ->
            if (apiStatus == ApiStatus.ERROR)
                binding.reloading.visibility = View.VISIBLE
            else
                binding.reloading.visibility = View.GONE
        })
        binding.reloading.setOnClickListener {
            viewModel.reLoading()
        }
        return binding.root
    }
}