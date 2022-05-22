package com.skillage.eduschool.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillage.eduschool.R
import com.skillage.eduschool.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val intent = Intent(this, RegisterActivity::class.java)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.btnRegister.setOnClickListener {
            startActivity(intent)
        }
    }
}