package com.example.fda_android

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fda_android.databinding.ActivityMainBinding
import com.example.fda_android.fragments.BrowseScreen
import com.example.fda_android.fragments.HomeScreen
import com.example.fda_android.fragments.RestaurantScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navItems: List<LinearLayout>
    private val fragments: List<Fragment> by lazy {
        listOf(
            HomeScreen(),
            BrowseScreen(),        //Browse
            HomeScreen(),              //Cart
            RestaurantScreen(),        //Orders
            HomeScreen(),              //Account
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navItems = listOf(
            binding.customNavBar.navHome,
            binding.customNavBar.navBrowse,
            binding.customNavBar.navCart,
            binding.customNavBar.navOrders,
            binding.customNavBar.navAccount
        )

        navItems.forEachIndexed { index, view ->
            view.setOnClickListener { loadFragment(index) }
        }

        loadFragment(0)

        supportFragmentManager.addOnBackStackChangedListener {
            updateNavBarVisibility()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (fragment is HomeScreen) {
            fragment.handleBackPress()
        } else {
            super.onBackPressed()
        }
    }

    private fun updateNavBarVisibility() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)

        if (currentFragment is RestaurantScreen) {
            binding.customNavBar.root.visibility = View.GONE
        } else {
            binding.customNavBar.root.visibility = View.VISIBLE
        }
    }

    private fun loadFragment(index: Int) {
        navItems.forEach { it.isSelected = false }
        navItems[index].isSelected = true

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragments[index])
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}