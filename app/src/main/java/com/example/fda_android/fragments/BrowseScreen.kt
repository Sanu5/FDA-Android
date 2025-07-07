package com.example.fda_android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fda_android.R
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.databinding.FragmentBrowseBinding
import com.example.fda_android.ui.adapter.RestaurantAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.BrowseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseScreen: Fragment() {
    private val viewModel: BrowseViewModel by viewModels()
    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private lateinit var restaurantAdapter: RestaurantAdapter

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

        viewModel.fetchRestaurantList()

        lifecycleScope.launchWhenStarted {
            viewModel.resState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                        Log.e("BrowseScreen", "Error: ${state.message}")
                    }
                    UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
//                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = state.data
                        setupRestaurantList(response.data)
                    }
                }
            }
        }

        restaurantAdapter = RestaurantAdapter(emptyList(), ::onRestaurantClick)

        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.setHasFixedSize(true)
        binding.rxRestaurantList.adapter = restaurantAdapter
    }

    private fun setupRestaurantList(response: List<RestaurantItem>) {
        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.adapter = RestaurantAdapter(
            restaurantList = response,
            onClick = ::onRestaurantClick
        )
    }

    private fun onRestaurantClick(restaurantId: String?) {
        if (restaurantId == null) return

        val fragment = RestaurantScreen().apply {
            arguments = Bundle().apply {
                putString("restaurantId", restaurantId)
            }
        }

        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .replace(R.id.main_fragment_container, fragment)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}