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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        shoppingCartFactory = ShoppingCartFactory((activity)!!.application)
        shoppingCartViewModel = ViewModelProvider(this, shoppingCartFactory).get(ShoppingCartViewModel::class.java)
        binding.viewModel = shoppingCartViewModel
        binding.shoppingRecycler.adapter = ShoppingAdapter(
            ListItemClickListener {

            },
            { old, new -> old.id == new.id },
            { old, new -> old == new })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        shoppingCartViewModel.getShopped()
    }
}