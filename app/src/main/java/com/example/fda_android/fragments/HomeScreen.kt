package com.example.fda_android.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fda_android.R
import com.example.fda_android.data.CouponItem
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.databinding.FragmentHomeScreenBinding
import com.example.fda_android.ui.adapter.OfferAdapter
import com.example.fda_android.ui.adapter.RestaurantAdapter
import com.google.android.material.switchmaterial.SwitchMaterial

class HomeScreen : Fragment() {
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
        setupOfferList()
        setupRestaurantList()

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
    }

    private fun setupOfferList(){
        val dummyOffers = listOf(
            CouponItem("1", "₹100 OFF", "On orders above ₹499", R.drawable.sample_food),
            CouponItem("2", "Buy 1 Get 1", "On selected items", R.drawable.sample_food),
            CouponItem("3", "20% Cashback", "Up to ₹75", R.drawable.sample_food)
        )
        binding.rvCouponList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCouponList.adapter = OfferAdapter(dummyOffers)
    }

    private fun setupRestaurantList(){
        val dummyRestaurants = listOf(
            RestaurantItem(
                id = "res1",
                name = "Spice Villa",
                type = "North Indian",
                rating = "4.5",
                image = R.drawable.sample_food,
                deliveryFee = "Free"
            ),
            RestaurantItem(
                id = "res2",
                name = "Dragon Express",
                type = "Chinese",
                rating = "4.2",
                image = R.drawable.sample_food,
                deliveryFee = "₹20"
            ),
            RestaurantItem(
                id = "res3",
                name = "Urban Bites",
                type = "Continental",
                rating = "4.7",
                image = R.drawable.sample_food,
                deliveryFee = "₹15"
            ),
            RestaurantItem(
                id = "res3",
                name = "Urban Bites",
                type = "Continental",
                rating = "4.7",
                image = R.drawable.sample_food,
                deliveryFee = "₹15"
            ),
            RestaurantItem(
                id = "res3",
                name = "Urban Bites",
                type = "Continental",
                rating = "4.7",
                image = R.drawable.sample_food,
                deliveryFee = "₹15"
            ),
            RestaurantItem(
                id = "res3",
                name = "Urban Bites",
                type = "Continental",
                rating = "4.7",
                image = R.drawable.sample_food,
                deliveryFee = "₹15"
            )
        )

        binding.rxRestaurantList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rxRestaurantList.adapter = RestaurantAdapter(dummyRestaurants)
    }

    fun handleBackPress() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

}