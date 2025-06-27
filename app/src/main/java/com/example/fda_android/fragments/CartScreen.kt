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
import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.ItemData
import com.example.fda_android.databinding.CartViewBinding
import com.example.fda_android.ui.adapter.CartItemAdapter
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartScreen() : Fragment() {
    private val viewModel: CartViewModel by viewModels()
    private var _binding: CartViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observeCartState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        setupCloseButton()
        setupCheckoutButton()
        setupNoteSection()
        fetchCartData()
    }

    private fun setupCloseButton() {
        binding.btnClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener {
            TODO()
        }
    }

    private fun setupNoteSection() {
        binding.tvNoteHint.setOnClickListener {
            TODO()
        }
    }

    private fun fetchCartData(token: String) {
        viewModel.fetchCartData(token)
    }

    private fun observeCartState() {
        lifecycleScope.launchWhenStarted {
            viewModel.cartState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        showEmptyState()
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                    }
                    UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val cartResponse = state.data
                        bindCartData(cartResponse)
                    }
                }
            }
        }
    }

    private fun bindCartData(cartResponse: CartResponse) {
        val cartData = cartResponse.data
        if(cartData != null){
            binding.restaurantNameCart.text = cartData.restaurantData?.restaurantName ?: "Restaurant"
            binding.restaurantAddressCart.text = cartData.restaurantData?.floatingView?.address ?: "Address unavailable"

            binding.itemCount.text = "${cartData.cartItemCount ?: "0"} items"
            binding.tvSubtotalValue.text = cartData.subtotal ?: "0.00"
            binding.tvNoteHint.text = cartData.noteForRestaurant ?: "Add a note to the order for restaurant"
            setupCartItems(cartData.itemData ?: emptyList())
        }
    }

    private fun setupCartItems(items: List<ItemData>){
        binding.cartItemRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CartItemAdapter(items){ itemId, newQuantity ->
                viewModel.updateItemQuantity(itemId, newQuantity)
            }
        }
    }

    private fun showEmptyState(){
        binding.emptyStateView.visibility = View.VISIBLE
        binding.clRootCart.visibility = View.GONE
    }
}