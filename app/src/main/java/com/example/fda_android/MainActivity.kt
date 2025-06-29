package com.example.fda_android

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fda_android.databinding.ActivityMainBinding
import com.example.fda_android.fragments.BrowseScreen
import com.example.fda_android.fragments.CartScreen
import com.example.fda_android.fragments.HomeScreen
import com.example.fda_android.fragments.RestaurantScreen
import com.example.fda_android.utils.openFragment
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
            CartScreen(),              //Cart
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

        val tag = fragments[index].javaClass.simpleName
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)

        if(existingFragment != null && existingFragment.isVisible) return

        openPage(
            fragment = fragments[index],
            tag = fragments[index].javaClass.simpleName,
            addToBackStack = true,
            add = true
        )
    }

    @SuppressLint("CommitTransaction")
    private fun openPage(fragment : Fragment, tag : String, addToBackStack: Boolean = false, add : Boolean = false, animate : Boolean = false) {
        supportFragmentManager.beginTransaction().openFragment(fragment = fragment, containerId = R.id.main_fragment_container, tag = tag, addToBackStack = addToBackStack, add = add, animate = animate)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}