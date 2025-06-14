package com.example.fda_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fda_android.databinding.ActivityRegistrationBinding

class RegistrationActivity: AppCompatActivity() {
    private var _binding : ActivityRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etName.apply {
            requestFocus()
            showSoftInputOnFocus = false
        }

        binding.btContinue.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}