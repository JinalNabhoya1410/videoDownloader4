package com.allin.videospro.ui.activity.common

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityPolicyBinding
import com.allin.videospro.ui.base.BaseActivity

class PolicyActivity : BaseActivity() {
    private lateinit var binding: ActivityPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onBackPressedDispatcher.addCallback { finish() }
        binding.textView.text = HtmlCompat.fromHtml(
            getString(R.string.policy_info),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
}