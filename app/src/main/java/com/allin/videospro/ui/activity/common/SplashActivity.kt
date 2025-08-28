package com.allin.videospro.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivitySplashBinding
import com.allin.videospro.util.areAllPermissionsGranted

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        binding.apply {
            textView.text = HtmlCompat.fromHtml(
                getString(R.string.all_video_downloader),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

        }

        Handler(mainLooper).postDelayed({
            if (areAllPermissionsGranted(this)){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,LanguageActivity::class.java))
                finish()
            }
        },2000)
    }
}