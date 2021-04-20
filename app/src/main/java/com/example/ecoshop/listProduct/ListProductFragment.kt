package com.example.ecoshop.listProduct

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoshop.R
import com.example.ecoshop.databinding.FragmentListProductBinding

class ListProductFragment : Fragment() {

    private val viewModel: ListProductViewModel by lazy {
        ViewModelProvider(this).get(ListProductViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListProductBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.listProductViewModel = viewModel
        val adapter = ListProductAdapter()
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}