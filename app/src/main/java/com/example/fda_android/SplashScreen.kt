package com.example.fda_android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fda_android.databinding.ActivitySplashBinding
import com.example.fda_android.utils.TokenManager

class SplashScreen : AppCompatActivity() {

    private var _binding : ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        fadeIn.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(a: Animation) {}
            override fun onAnimationRepeat(a: Animation) {}
            override fun onAnimationEnd(a: Animation) {
                binding.tvBottom.visibility = View.VISIBLE
                binding.tvBottom.startAnimation(slideUp)
            }
        })

        slideUp.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(a: Animation) {}
            override fun onAnimationRepeat(a: Animation) {}
            override fun onAnimationEnd(a: Animation) {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (tokenManager.getToken() == null){
                        startActivity(Intent(this@SplashScreen, RegistrationActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    }
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }, 1500)
            }
        })

        binding.tvMain.visibility = View.VISIBLE
        binding.tvMain.startAnimation(fadeIn)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}