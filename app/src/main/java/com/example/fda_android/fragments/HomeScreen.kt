package com.example.fda_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.databinding.FragmentHomeScreenBinding
import com.example.fda_android.ui.adapter.OfferAdapter
import com.example.fda_android.ui.adapter.RestaurantAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.HomeViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class HomeScreen : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var closeBtn: ImageView
    private lateinit var darkModeSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout = binding.drawerLayout
        closeBtn = binding.closeBtn
        darkModeSwitch = binding.darkModeSwitch

        binding.menuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        closeBtn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), if(isChecked) "Dark Mode Enabled" else "Dark Mode Disabled", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.homeState.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                    }
                    UiState.Loading -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Success<*> -> {
                        val response = state.data as HomeResponse
                        setupOfferList(response)
                        setupRestaurantList(response)
                    }
                }
            }
        }
    }

    private fun setupOfferList(response: HomeResponse) {
        binding.rvCouponList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCouponList.adapter = OfferAdapter(response.data.couponView)
    }

    private fun setupRestaurantList(response: HomeResponse){
        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.adapter = RestaurantAdapter(response.data.restaurantList)
    }

    fun handleBackPress() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}