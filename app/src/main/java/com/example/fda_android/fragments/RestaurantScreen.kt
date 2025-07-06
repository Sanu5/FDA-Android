package com.example.fda_android.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fda_android.R
import com.example.fda_android.data.RestaurantData
import com.example.fda_android.data.RestaurantViewResponse
import com.example.fda_android.databinding.FragmentRestaurantDetailBinding
import com.example.fda_android.ui.adapter.MenuItemAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.CartViewModel
import com.example.fda_android.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur
import kotlin.getValue

@AndroidEntryPoint
class RestaurantScreen(): Fragment() {

    private val viewModel: RestaurantViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private var _binding : FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuAdapter: MenuItemAdapter

    private val restaurantId: String? by lazy {
        requireArguments().getString("restaurantId") ?: throw IllegalStateException("Must pass a restaurantId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndBlur()
        setupRecyclerView()
        observeRestaurantResponse()
    }

    private fun setupRecyclerView() {
        menuAdapter = MenuItemAdapter(
            items = emptyList(),
            onItemClick = ::onMenuItemClick,
            onAddClick = ::onItemAdd
        )

        binding.rvMenuList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = menuAdapter
        }
    }

    private fun observeRestaurantResponse() {
        viewModel.fetchRestaurantData(restaurantId!!)

        lifecycleScope.launchWhenStarted {
            viewModel.restaurantState.collect { state ->
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
                        menuAdapter.updateData(response.data.featuredItemsList)
                        setupView(response.data)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupToolbarAndBlur() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val decor = requireActivity().window.decorView as ViewGroup
        val windowBackground = requireActivity().window.decorView.background
        binding.blurView.setupWith(decor, RenderEffectBlur())
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(3f)
            .setBlurAutoUpdate(true)
    }

    private fun setupView(response: RestaurantData) {
        Glide.with(binding.ivMainFood.context).clear(binding.ivMainFood)
        Glide.with(binding.ivMainFood.context)
            .load(response.restaurantImage)
            .into(binding.ivMainFood)

        binding.tvRestaurantName.text = response.floatingView?.name
        binding.tvRestaurantAddress.text = response.floatingView?.address
        binding.tvDeliveryFee.text = "Delivery Fee\n$${response.floatingView?.deliveryFee}"
        binding.tvDeliveryTime.text = "Delivery Time\n${response.floatingView?.deliveryTime}"
        binding.tvRating.text = "Rating/Review\n${response.floatingView?.rating}"
    }

    private fun setupItemList(response: RestaurantViewResponse) {
        binding.rvMenuList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMenuList.adapter = MenuItemAdapter(
            items = response.data.featuredItemsList,
            onItemClick = ::onMenuItemClick,
            onAddClick = ::onItemAdd,
        )
    }

    private fun onItemAdd(itemId: String?) {
        cartViewModel.addCartItem(restaurantId = restaurantId, itemId = itemId, itemQuantity = 1)
        Toast.makeText(requireContext(), "Item added to cart", Toast.LENGTH_SHORT).show()
    }

    private fun onMenuItemClick(itemId: String?) {
        if(itemId == null) return

        val fragment = ItemScreen().apply {
            arguments = Bundle().apply {
                putString("itemId", itemId)
                putString("restaurantId", restaurantId)
            }
        }

        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_RESTAURANT_ID = "restaurantId"

        fun newInstance(restaurantId: String): RestaurantScreen {
            return RestaurantScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESTAURANT_ID, restaurantId)
                }
            }
        }
    }
}