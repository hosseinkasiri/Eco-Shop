package com.example.ecoshop.detailCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.databinding.FragmentDetailCategoryBinding
import com.example.ecoshop.model.ProductCategory
import com.example.ecoshop.utils.ListItemClickListener

class DetailCategoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailCategoryBinding
    private lateinit var viewModel: DetailCategoryViewModel
    private lateinit var viewModelFactory: DetailCategoryFactory
    private lateinit var productCategory: ProductCategory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailCategoryBinding.inflate(layoutInflater)
        productCategory = DetailCategoryFragmentArgs.fromBundle(requireArguments()).productCategory
        viewModelFactory = DetailCategoryFactory(productCategory, (activity)!!.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailCategoryViewModel::class.java)
        binding.viewModel = viewModel
        binding.recyclerDetailCategory.adapter = ListProductVerticalAdapter(ListItemClickListener {

        },
                {old, new -> old.id == new.id },
                {old, new ->  old == new})
        return binding.root
    }
}