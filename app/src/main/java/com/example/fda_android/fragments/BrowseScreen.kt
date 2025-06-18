package com.example.fda_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.databinding.FragmentBrowseBinding
import com.example.fda_android.ui.adapter.RestaurantAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.BrowseViewModel

class BrowseScreen: Fragment() {
    private val viewModel: BrowseViewModel by viewModels()
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.resState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                    }
                    UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = state.data
                        setupRestaurantList(response)
                    }
                }
            }
        }
    }

    private fun setupRestaurantList(response: List<RestaurantItem>) {
        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.adapter = RestaurantAdapter(response)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}