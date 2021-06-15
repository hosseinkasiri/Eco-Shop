package com.example.ecoshop.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ecoshop.R
import com.example.ecoshop.customViews.ListProductVerticalAdapter
import com.example.ecoshop.databinding.FragmentSearchBinding
import com.example.ecoshop.home.ApiStatus
import com.example.ecoshop.utils.ListItemClickListener

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var search: SearchView
    private lateinit var adapter: ListProductVerticalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewModel = viewModel
        search = binding.searchView
        handleSearchView()
        handleSearchingError()
        bindRecycler()
        return binding.root
    }

    private fun bindRecycler() {
        adapter = ListProductVerticalAdapter(ListItemClickListener { product ->

        }, { old, new -> old.id == new.id },
            { old, new -> old == new })
        viewModel.property.observe(viewLifecycleOwner, Observer { products ->
            adapter.submitList(products)
        })
        binding.searchRecycler.adapter = adapter
    }

    private fun handleSearchView() {
        search.isIconified = false
        search.isIconifiedByDefault = false
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let { viewModel.startTimerProducts(p0) }
                return true
            }
        })
    }

    private fun handleSearchingError() {
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