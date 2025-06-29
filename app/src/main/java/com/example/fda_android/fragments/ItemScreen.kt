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
import com.bumptech.glide.Glide
import com.example.fda_android.data.ItemData
import com.example.fda_android.databinding.ItemViewBinding
import com.example.fda_android.utils.UiState
import com.example.fda_android.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemScreen: Fragment() {
    private val viewmodel: ItemViewModel by viewModels()
    private var _binding: ItemViewBinding? = null
    private val binding get() = _binding!!

    private val itemId : String? by lazy {
        requireArguments().getString(ARG_ITEM_ID) ?: throw IllegalStateException("Must pass an itemId")
    }

    private val restaurantId: String? by lazy {
        requireArguments().getString(ARG_RESTAURANT_ID) ?: throw IllegalStateException("Must pass an restaurantId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val blurView: BlurView = binding.blurView
        val decor = requireActivity().window.decorView as ViewGroup
        val windowBackground = requireActivity().window.decorView.background
        blurView.setupWith(decor, RenderEffectBlur())
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(3f)
            .setBlurAutoUpdate(true)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewmodel.fetchItemData(restaurantId, itemId)

        lifecycleScope.launch {
            viewmodel.itemState.collect { state ->
                when (state) {
                    is UiState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is UiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is UiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setupItemDetail(state.data.data)
                    }
                    is UiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message ?: "Add to Cart failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupItemDetail(item: ItemData) {
        binding.tvItemName.text = item.itemName
        binding.tvItemDes.text = item.itemDescription
        binding.tvItemDeliveryFee.text = item.itemPrice

        Glide.with(binding.ivItemImage.context)
            .load(item.itemImage)
            .into(binding.ivItemImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ITEM_ID = "itemId"
        private const val ARG_RESTAURANT_ID = "restaurantId"

        fun newInstance(restaurantId: String?, itemId: String): ItemScreen {
            return ItemScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_ITEM_ID, itemId)
                    putString(ARG_RESTAURANT_ID, restaurantId)
                }
            }
        }
    }
}