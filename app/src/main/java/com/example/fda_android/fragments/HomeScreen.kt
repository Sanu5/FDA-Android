package com.example.fda_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fda_android.R
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.databinding.FragmentHomeScreenBinding
import com.example.fda_android.ui.adapter.OfferAdapter
import com.example.fda_android.ui.adapter.RestaurantAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.HomeViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding : FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var offerAdapter: OfferAdapter
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeHomeScreenResponse()
    }

    private fun init() {
        setupData()
        setListeners()

        offerAdapter = OfferAdapter(emptyList())
        restaurantAdapter = RestaurantAdapter(emptyList(), ::onRestaurantClick)

        binding.rvCouponList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCouponList.setHasFixedSize(true)
        binding.rvCouponList.adapter = offerAdapter

        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.setHasFixedSize(true)
        binding.rxRestaurantList.adapter = restaurantAdapter
    }

    private fun setupData() {
        val customview = layoutInflater.inflate(R.layout.toolbar_home_custom_view, binding.topBar, false)
        binding.topBar.addView(customview)

        customview.findViewById<ImageView>(R.id.menu_icon).setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setListeners() {
        binding.closeBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), if(isChecked) "Dark Mode Enabled" else "Dark Mode Disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeHomeScreenResponse() {
        lifecycleScope.launchWhenStarted {
            viewModel.homeState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Error: ${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }

                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = state.data
                        setupOfferList(response)
                        setupRestaurantList(response)
                    }
                }
            }
        }
    }

    private fun setupOfferList(response: HomeResponse) {
        binding.rvCouponList.post {
            binding.rvCouponList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvCouponList.adapter = OfferAdapter(response.data.couponView)
        }
    }

    private fun setupRestaurantList(response: HomeResponse){
        binding.rxRestaurantList.post {
            binding.rxRestaurantList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rxRestaurantList.adapter = RestaurantAdapter(
                restaurantList = response.data.restaurantList,
                onClick = ::onRestaurantClick
            )
        }
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

    fun handleBackPress() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): HomeScreen {
            return HomeScreen()
        }
    }
}