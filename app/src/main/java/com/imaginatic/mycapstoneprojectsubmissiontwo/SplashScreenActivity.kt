package com.imaginatic.mycapstoneprojectsubmissiontwo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imaginatic.mycapstoneprojectsubmissiontwo.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.root.alpha = 0f
        binding.root.animate().setDuration(1500).alpha(1f).withEndAction {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}