package com.allin.videospro.ui.activity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityDownloaderBinding
import com.allin.videospro.ui.adapter.FolderAdapter
import com.allin.videospro.ui.adapter.VpFolderAdapter
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.ui.fragment.FacebookFragment
import com.allin.videospro.ui.fragment.InstagramFragment
import com.allin.videospro.ui.fragment.JoshFragment
import com.allin.videospro.ui.fragment.MojFragment
import com.allin.videospro.ui.fragment.ShareChatFragment
import com.allin.videospro.ui.fragment.TikTokFragment
import com.allin.videospro.ui.fragment.TwitterFragment
import com.allin.videospro.ui.fragment.WhatsappFragment
import com.allin.videospro.util.OnFolderClick
import java.util.ArrayList

class DownloaderActivity : BaseActivity() {
    private lateinit var binding: ActivityDownloaderBinding
    var folderAdapter: FolderAdapter? = null
    var vpAdapter: VpFolderAdapter? = null
    var fragmentList: List<Fragment> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback { finish() }
        initView()
        initAdapter()
        initListener()
    }

    private fun initAdapter() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@DownloaderActivity, LinearLayoutManager.HORIZONTAL, false)

            folderAdapter = FolderAdapter(this@DownloaderActivity, object : OnFolderClick {
                override fun onClick(position: Int, list: ArrayList<String>) {
                    viewPager.currentItem = position
                }
            })

            recyclerView.adapter = folderAdapter

            vpAdapter = VpFolderAdapter(this@DownloaderActivity, fragmentList)
            viewPager.adapter = vpAdapter
        }
    }


    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun initView() {
        binding.apply {
            fragmentList = listOf(
                InstagramFragment(),
                FacebookFragment(),
                TwitterFragment(),
                WhatsappFragment(),
                JoshFragment(),
                TikTokFragment(),
                ShareChatFragment(),
                MojFragment(),
            )

            viewPager.adapter = VpFolderAdapter(this@DownloaderActivity, fragmentList)

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    folderAdapter?.setSelectedFromViewPager(position)
                    recyclerView.smoothScrollToPosition(position)
                }
            })
        }
    }

}