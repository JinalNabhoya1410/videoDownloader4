package com.allin.videospro.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityVideoDownloaderOptionBinding
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.Utility.isWhatsappInstalled

class VideoDownloaderOptionActivity : BaseActivity() {
    private lateinit var binding: ActivityVideoDownloaderOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDownloaderOptionBinding.inflate(layoutInflater)
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
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            instagram.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Instagram")
                    putExtra("image",R.drawable.ic_instagram)
                    startActivity(this)
                }
            }

            twitter.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Twitter")
                    putExtra("image",R.drawable.ic_twitter)
                    startActivity(this)
                }
            }

            facebook.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Facebook")
                    putExtra("image",R.drawable.ic_facebook)
                    startActivity(this)
                }
            }

            josh.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Josh")
                    putExtra("image",R.drawable.ic_josh)
                    startActivity(this)
                }
            }

            tiktok.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","TikTok")
                    putExtra("image",R.drawable.ic_tiktok)
                    startActivity(this)
                }
            }

            shareChat.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Share Chat")
                    putExtra("image",R.drawable.ic_share_chat)
                    startActivity(this)
                }
            }

            moj.setOnClickListener {
                Intent(this@VideoDownloaderOptionActivity,VideoDownloadActivity::class.java).apply {
                    putExtra("name","Moj")
                    putExtra("image",R.drawable.ic_moj)
                    startActivity(this)
                }
            }

            whatsapp.setOnClickListener {
                if (isWhatsappInstalled()) {
                    Intent(this@VideoDownloaderOptionActivity, WhatsappActivity::class.java).apply {
                        putExtra(AppConstance.FROM_WP, true)
                        putExtra(AppConstance.IMAGE, R.drawable.ic_whatsapp)
                        startActivity(this)
                    }
                }else{
                    Toast.makeText(this@VideoDownloaderOptionActivity, getString(R.string.whatsapp_is_not_installed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}