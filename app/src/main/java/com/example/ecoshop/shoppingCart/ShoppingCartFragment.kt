package com.example.ecoshop.shoppingCart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
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
        updateData()
        bindRecycler()
        shoppingCartViewModel.totalDiscounts.observe(viewLifecycleOwner, Observer { discounts ->
            setPrices(binding.totalDiscounts, discounts)
        })
        shoppingCartViewModel.totalShopped.observe(viewLifecycleOwner, Observer { prises ->
            setPrices(binding.totalShoppingCart, prises)
        })
        shoppingCartViewModel.totalPrises.observe(viewLifecycleOwner, Observer { prices ->
            setPrices(binding.totalPrises, prices)
        })
        return binding.root
    }

    private fun updateData() {
        shoppingCartViewModel.getShopped()
        shoppingCartViewModel.getDiscounts()
        shoppingCartViewModel.getPrises()
        shoppingCartViewModel.getTotalShopping()
        shoppingCartViewModel.fillShoppingItem()
    }

    private fun bindRecycler() {
        shoppingAdapter = ShoppingAdapter(
            ListItemClickListener { product ->
                shoppingCartViewModel.deleteProduct(product)
                updateData()
                notifyRecycler()
            },
            { old, new -> old.product == new.product},
            { old, new -> old == new })
        shoppingAdapter.submitList(shoppingCartViewModel.shoppingItems.value)
        binding.shoppingRecycler.adapter = shoppingAdapter
    }

    private fun notifyRecycler(){
        shoppingAdapter.submitList(shoppingCartViewModel.shoppingItems.value)
        shoppingAdapter.notifyDataSetChanged()
    }

    private fun setPrices(textView: TextView, price: String?){
        var array = ArrayList<Char>()
        val charArray = price!!.toCharArray()
        var number = 1
        for (i in charArray.size-1 downTo 0){
            if (number%3 == 0 && number != 1 && number!=charArray.size){
                array.add(charArray[i])
                array.add(',')
                number++
                continue
            }
            array.add(charArray[i])
            number++
        }
        array.reverse()
        textView.text = array.joinToString("") + " " + "تومان"
    }
}