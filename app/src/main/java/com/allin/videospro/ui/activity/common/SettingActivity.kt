package com.allin.videospro.ui.activity.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivitySettingBinding
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.Utility.rateApp
import com.allin.videospro.util.Utility.shareApp

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback { finish() }
        initListener()
    }

    private fun initListener() {
        binding.apply {
            language.setOnClickListener {
                Intent(this@SettingActivity, LanguageActivity::class.java).apply {
                    putExtra(AppConstance.FROM_SETTING,true)
                    startActivity(this)
                }
            }

            policy.setOnClickListener {
                startActivity(Intent(this@SettingActivity, PolicyActivity::class.java))
            }

            share.setOnClickListener {
                shareApp()
            }

            rate.setOnClickListener {
                rateApp()
            }

            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
}