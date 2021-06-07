package com.example.ecoshop.shoppingCart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ecoshop.customViews.ShoppingAdapter
import com.example.ecoshop.databinding.FragmentShoppingCartBinding
import com.example.ecoshop.utils.ListItemClickListener

class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private lateinit var shoppingCartFactory: ShoppingCartFactory
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var shoppingAdapter: ShoppingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        shoppingCartFactory = ShoppingCartFactory((activity)!!.application)
        shoppingCartViewModel = ViewModelProvider(this, shoppingCartFactory).get(ShoppingCartViewModel::class.java)
        binding.viewModel = shoppingCartViewModel
        bindRecycler()
        return binding.root
    }

    private fun bindRecycler() {
        shoppingAdapter = ShoppingAdapter(
            ListItemClickListener { product ->
                shoppingCartViewModel.deleteProduct(product)
                notifyRecycler()
            },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        shoppingAdapter.submitList(shoppingCartViewModel.shoppedProducts.value)
        binding.shoppingRecycler.adapter = shoppingAdapter
    }

    private fun notifyRecycler(){
        shoppingAdapter.submitList(shoppingCartViewModel.shoppedProducts.value)
        shoppingAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        shoppingCartViewModel.getShopped()
    }
}