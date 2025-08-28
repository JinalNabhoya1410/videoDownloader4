package com.allin.videospro.ui.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityVideoDownloadBinding
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.util.AppConstance
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

class VideoDownloadActivity : BaseActivity() {
    private lateinit var binding: ActivityVideoDownloadBinding
    var downloadId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDownloadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback { finish() }
        initView()
        initListener()
    }

    private fun initView() {
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            name.text = intent.getStringExtra("name")
            img.setImageResource(intent.getIntExtra("image", 0))
        }
    }

    private fun initListener() {
        binding.apply {
            btnPaste.setOnClickListener {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = clipboard.primaryClip

                if (clipData != null && clipData.itemCount > 0) {
                    val pastedText = clipData.getItemAt(0).text.toString()
                    binding.etLink.setText(pastedText)
                } else {
                    Toast.makeText(this@VideoDownloadActivity, "Nothing to paste", Toast.LENGTH_SHORT).show()
                }
            }

            btnDownload.setOnClickListener {
                hideKeyboard()
                when (intent.getStringExtra("name")) {
                    "Instagram" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("www.instagram.com")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.INSTAGRAM)
                        }
                    }

                    "Twitter" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("x.com")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.TWITTER)
                        }
                    }

                    "Facebook" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("www.facebook.com")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.FACEBOOK)
                        }
                    }

                    "Josh" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("myjosh.in")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.JOSH)
                        }
                    }

                    "TikTok" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("tiktok")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.TIKTOK)
                        }
                    }

                    "Share Chat" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("sharechat.com")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.SHARECHAT)
                        }
                    }

                    "Moj" -> {
                        if (etLink.text.isNullOrEmpty() || !etLink.text.toString()
                                .contains("mojapp.in")
                        ) {
                            Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_enter_valid_link), Toast.LENGTH_SHORT).show()
                        } else {
                            btnDownload.text = getString(R.string.downloading)
                            fetchSnapVideos(etLink.text.toString(),AppConstance.MOJ)
                        }
                    }
                }
            }
        }
    }

    fun getDownloadPath(folderName: String): String {
        val appName = getString(R.string.app_name)
        val folder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "$appName/$folderName"
        )
        if (!folder.exists()) folder.mkdirs()
        return folder.absolutePath
    }

    fun downloadVideo(url: String, folderName: String) {
        downloadId = PRDownloader.download(
            url,
            getDownloadPath(folderName),
            "${folderName}_${System.currentTimeMillis()}.mp4"
        )
            .build()
            .setOnProgressListener {
                val progress = if (it.totalBytes > 0) {
                    (it.currentBytes * 100 / it.totalBytes).toInt()
                } else 0
                binding.btnDownload.text = "$progress%"
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Log.e("TAG", "onDownloadComplete: ")
                    binding.btnDownload.text = getString(R.string.downloaded)
                    Toast.makeText(this@VideoDownloadActivity, getString(R.string.video_download_successfully), Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: com.downloader.Error?) {
                    Log.e("TAG", "onError: ${error?.serverErrorMessage}")
                    Toast.makeText(this@VideoDownloadActivity, getString(R.string.please_try_again_later), Toast.LENGTH_SHORT).show()
                    binding.btnDownload.text = getString(R.string.download)
                    binding.etLink.setText("")
                }
            })
    }

    private fun fetchSnapVideos(postUrl: String,name:String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()

                val formBody = FormBody.Builder()
                    .add("url", postUrl)
                    .build()

                val request = Request.Builder()
                    .url("https://snap-video3.p.rapidapi.com/download")
                    .post(formBody)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("X-RapidAPI-Key", "5352c35c85msh3c334e4c6f926acp1544dbjsn7617d5771c2e")
                    .addHeader("X-RapidAPI-Host", "snap-video3.p.rapidapi.com")
                    .build()

                val response = client.newCall(request).execute()
                val bodyStr = response.body?.string()

                Log.e("TAG", "Code: ${response.code}, Body: $bodyStr")

                if (!bodyStr.isNullOrEmpty()) {
                    val json = JSONObject(bodyStr)

                    var finalUrl: String? = null

                    if (json.has("medias")) {
                        val medias = json.getJSONArray("medias")
                        if (medias.length() > 0) {
                            val firstMedia = medias.getJSONObject(0)
                            if (firstMedia.has("url")) {
                                finalUrl = firstMedia.getString("url")
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        if (finalUrl != null) {
                            downloadVideo(finalUrl, name)
                        } else {
                            Toast.makeText(this@VideoDownloadActivity, "No download link found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@VideoDownloadActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}