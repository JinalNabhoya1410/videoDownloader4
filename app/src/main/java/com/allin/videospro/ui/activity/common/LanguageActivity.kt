package com.allin.videospro.ui.activity.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityLanguageBinding
import com.allin.videospro.ui.adapter.LanguageAdapter
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.ui.model.Language
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.OnLangClick
import com.allin.videospro.util.TinyDB
import com.allin.videospro.util.getLanguageList

class LanguageActivity : BaseActivity() {
    private lateinit var binding: ActivityLanguageBinding
    var adapter: LanguageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initAdapter()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            next.setOnClickListener {
                val selectedLang = TinyDB(this@LanguageActivity).getString(AppConstance.SELECTED_LANGUAGE)
                changeLanguage(selectedLang)
                if (intent.getBooleanExtra(AppConstance.FROM_SETTING,false)) {
                    finish()
                } else {
                    startActivity(Intent(this@LanguageActivity, SelectionActivity1::class.java))
                    finish()
                }
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@LanguageActivity)
            adapter = LanguageAdapter(this@LanguageActivity,getLanguageList(),object : OnLangClick{
                override fun onClick(list: Language) {
                    TinyDB(this@LanguageActivity).putString(AppConstance.SELECTED_LANGUAGE,list.code)
                }
            })
            recyclerView.adapter = adapter
        }
    }
    private fun changeLanguage(languageCode: String) {
        TinyDB(this).putString("selectedLanguage", languageCode)
        setLocale(this,languageCode)
    }
}