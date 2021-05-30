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
        binding.categoriesRecycler.adapter = CategoryAdapter(ListItemClickListener {
            viewModel.displayPropertyDetails(it)
        },
            {old, new -> old.id == new.id },
            {old, new ->  old == new})
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null){
                findNavController().navigate(CategoriesFragmentDirections.
                actionCategoriesFragmentToDetailCategoryFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        return binding.root
    }
}