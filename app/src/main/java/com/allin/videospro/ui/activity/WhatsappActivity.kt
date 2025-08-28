package com.allin.videospro.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityWhatsappBinding
import com.allin.videospro.ui.adapter.WhatsappAdapter
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.ui.model.StatusModel
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.OnDownloadClick
import com.allin.videospro.util.getRootDirPath
import com.allin.videospro.util.getWhatsappMedia
import java.io.File
import java.io.FileOutputStream

class WhatsappActivity : BaseActivity() {
    private lateinit var binding: ActivityWhatsappBinding
    var adapter: WhatsappAdapter? = null
    val PREF_URI_KEY = "WHATSAPP_URI"
    var savedUri: String? = null
    var mediaType: String = "images"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatsappBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback { finish() }

    }


    private fun initAdapter() {
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(this@WhatsappActivity, 3)
            if (savedUri == null) return

            val mediaList = getWhatsappMedia(Uri.parse(savedUri), mediaType)

            adapter = WhatsappAdapter(this@WhatsappActivity, mediaList, mediaType,
                object : OnDownloadClick {
                    override fun onClick(list: ArrayList<StatusModel>, position: Int) {
                        mediaDownload(list[position].path, list[position].name)
                    }
                })

            recyclerview.adapter = adapter
        }
    }

    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            images.setOnClickListener {
                mediaType = "images"
                images.setBackgroundResource(R.drawable.bg_accent_border_5)
                video.setBackgroundResource(0)
                updateMediaList()
            }

            video.setOnClickListener {
                mediaType = "videos"
                video.setBackgroundResource(R.drawable.bg_accent_border_5)
                images.setBackgroundResource(0)
                updateMediaList()
            }
        }
    }

    private fun initView() {
        savedUri = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getString(PREF_URI_KEY, null)
        if (savedUri.isNullOrEmpty()) {
            checkWhatsAppPermission()
        }
    }

    private fun updateMediaList() {
        if (!savedUri.isNullOrEmpty()) {
            val list = getWhatsappMedia(Uri.parse(savedUri), mediaType)
            if (adapter == null) initAdapter()
            adapter?.updateList(list)
        }
    }

    private fun checkWhatsAppPermission() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        val wa_status_uri =
            "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses".toUri()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, wa_status_uri)
        }
        intent.addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        )
        resultLauncher.launch(intent)
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val treeUri = result.data!!.data ?: return@registerForActivityResult

            contentResolver.takePersistableUriPermission(
                treeUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )

            val uriString = treeUri.toString()
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
                .putString(PREF_URI_KEY, uriString).apply()

            savedUri = uriString
            updateMediaList()
        }
    }


    fun mediaDownload(contentUri: String, fileName: String) {
        try {
            val inputStream = contentResolver.openInputStream(Uri.parse(contentUri))
            if (inputStream != null) {
                val fileExtension = if (fileName.endsWith(".mp4")) ".mp4" else ".jpg"
                val outputFile = File(getRootDirPath(this, AppConstance.WHATSAPP), "Whatsapp_${System.currentTimeMillis()}$fileExtension")
                val outputStream = FileOutputStream(outputFile)
                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()
            } else {
                Toast.makeText(this, getString(R.string.unable_to_open_file), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}