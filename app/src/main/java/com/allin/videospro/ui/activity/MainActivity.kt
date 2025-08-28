package com.app.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.app.R
import com.app.app.databinding.ActivityMainBinding
import com.app.app.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback { finishAffinity() }
        binding.textView.text = HtmlCompat.fromHtml(
            getString(R.string.play_tube_video_downloader),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        initListener()
    }

    private fun initListener() {
        binding.apply {
            videoDownloader.setOnClickListener {
                startActivity(Intent(this@MainActivity, VideoDownloaderOptionActivity::class.java))
            }

            downloaderVideo.setOnClickListener {
                startActivity(Intent(this@MainActivity, DownloaderActivity::class.java))
            }

            setting.setOnClickListener {
                startActivity(Intent(this@MainActivity,SettingActivity::class.java))
            }
        }
    }
}