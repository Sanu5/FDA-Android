package com.example.fda_android.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.fda_android.databinding.FragmentRestaurantDetailBinding
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur

class RestaurantScreen: Fragment() {

    private var _binding : FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!

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

        val blurView: BlurView = binding.blurView

        val decor = requireActivity().window.decorView as ViewGroup

        val windowBackground = requireActivity().window.decorView.background

        blurView.setupWith(decor, RenderEffectBlur())
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(4f)
            .setBlurAutoUpdate(true)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}