package com.example.fda_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fda_android.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

class SplashScreen : AppCompatActivity() {

    private var _binding : ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewTop = binding.tvMain
        val bottomText = binding.tvBottom
        val cta = binding.btContinue

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        textViewTop.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            bottomText.startAnimation(slideUp)
            cta.startAnimation(slideUp)
        }, 2200)


        Handler(Looper.getMainLooper()).postDelayed({
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }, 3500)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}