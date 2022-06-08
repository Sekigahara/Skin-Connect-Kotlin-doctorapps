package com.skinconnect.doctorapps.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.skinconnect.doctorapps.ui.auth.AuthActivity
import com.skinconnect.doctorapps.ui.auth.SplashViewModel
import com.skinconnect.doctorapps.ui.helper.BaseView
import com.skinconnect.doctorapps.ui.helper.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), BaseView {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupAction()
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(this)
        val viewModel: SplashViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        lifecycleScope.launch(Dispatchers.Default) {
            delay(1000)

            withContext(Dispatchers.Main) {
                viewModel.getUserToken().observe(this@SplashActivity) {
                    val intent =
                        if (it.isNullOrBlank() || it.isNullOrEmpty()) Intent(this@SplashActivity,
                            AuthActivity::class.java) else Intent(this@SplashActivity,
                            MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun setupView() {}
}