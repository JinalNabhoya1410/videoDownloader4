package com.allin.videospro.ui.activity.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivitySelection2Binding
import com.allin.videospro.ui.base.BaseActivity

class Selection2Activity : BaseActivity() {
    private lateinit var binding: ActivitySelection2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelection2Binding.inflate(layoutInflater)
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
            txt1.setOnClickListener { txt1.isSelected = !txt1.isSelected }
            txt2.setOnClickListener { txt2.isSelected = !txt2.isSelected }
            txt3.setOnClickListener { txt3.isSelected = !txt3.isSelected }
            txt4.setOnClickListener { txt4.isSelected = !txt4.isSelected }
            txt5.setOnClickListener { txt5.isSelected = !txt5.isSelected }
            txt6.setOnClickListener { txt6.isSelected = !txt6.isSelected }
            txt7.setOnClickListener { txt7.isSelected = !txt7.isSelected }
            txt8.setOnClickListener { txt8.isSelected = !txt8.isSelected }

            next.setOnClickListener {
                startActivity(Intent(this@Selection2Activity, SelectionActivity3::class.java))
                finish()
            }
        }
    }
}