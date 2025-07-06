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
    
    private lateinit var cartItemAdapter: CartItemAdapter

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
        setListeners()

        cartItemAdapter = CartItemAdapter(
            emptyList(),
            onQuantityChanged = { itemId, newQuantity ->
                viewModel.updateItemQuantity(itemId, newQuantity)
            }
        )
        binding.cartItemRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.cartItemRecycler.setHasFixedSize(true)
        binding.cartItemRecycler.adapter = cartItemAdapter
    }

    private fun setListeners() {
        binding.btnClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.checkoutButton.setOnClickListener {
            TODO()
        }

        binding.tvNoteHint.setOnClickListener {
            TODO()
        }
    }

    private fun observeCartState() {
        viewModel.fetchCartData()

        lifecycleScope.launchWhenStarted {
            viewModel.cartState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        //showEmptyState()
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                        Log.e("CartScreen", "Error: ${state.message}")
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
            binding.restaurantNameCart.text = cartData.restaurantData?.restaurantName
            binding.restaurantAddressCart.text = cartData.restaurantData?.address

            binding.itemCount.setText(cartData.cartItemCount.toString() + " " + getString(R.string.items))
            binding.tvSubtotalValue.text = cartData.subtotal
//            binding.tvNoteHint.text = cartData.noteForRestaurant
            Log.d("CartScreen", "Cart Data: $cartData")
            setupCartItems(cartData.items)
        }
    }

    private fun setupCartItems(items: List<ItemData>?){
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