package com.allin.videospro.ui.activity.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivitySelection1Binding
import com.allin.videospro.ui.base.BaseActivity

class SelectionActivity1 : BaseActivity() {
    private lateinit var binding: ActivitySelection1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelection1Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListener()
    }

    private fun initListener() {
        binding.apply {
            layout1.setOnClickListener {
                selection1.isSelected = true
                selection2.isSelected = false
                selection3.isSelected = false
            }

            layout2.setOnClickListener {
                selection1.isSelected = false
                selection2.isSelected = true
                selection3.isSelected = false
            }

            layout3.setOnClickListener {
                selection1.isSelected = false
                selection2.isSelected = false
                selection3.isSelected = true
            }

            next.setOnClickListener {
                startActivity(Intent(this@SelectionActivity1, Selection2Activity::class.java))
                finish()
            }
        }
    }
}