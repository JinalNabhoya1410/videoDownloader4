package com.allin.videospro.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.allin.videospro.R
import com.allin.videospro.databinding.ActivityVideoBinding
import com.allin.videospro.databinding.DialogDeleteBinding
import com.allin.videospro.ui.base.BaseActivity
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.Utility.isWhatsappInstalled
import com.allin.videospro.util.beGone
import com.allin.videospro.util.beVisible
import com.bumptech.glide.Glide
import java.io.File

class VideoActivity : BaseActivity() {
    private lateinit var binding: ActivityVideoBinding
    var path:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
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

    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            play.setOnClickListener {
                play.beGone()
                imgThumb.beGone()
                videoView.setVideoPath(path)
                videoView.setMediaController(MediaController(this@VideoActivity))
                videoView.start()
            }

            videoView.setOnCompletionListener {
                play.beVisible()
                imgThumb.beVisible()
            }

            shareWhatsapp.setOnClickListener {
                if (isWhatsappInstalled()){
                    val file = File(path)
                    val uri = FileProvider.getUriForFile(this@VideoActivity, "${packageName}.provider", file)

                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "*/*"
                        setPackage("com.whatsapp")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        putExtra(Intent.EXTRA_STREAM, uri)
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share to WhatsApp"))
                }else{
                    Toast.makeText(this@VideoActivity, getString(R.string.whatsapp_is_not_installed), Toast.LENGTH_SHORT).show()
                }
            }

            share.setOnClickListener {
                shareVideo(File(path))
            }

            delete.setOnClickListener {
                dialogDelete()
            }
        }
    }

    private fun initView() {
        binding.apply {
            path = intent.getStringExtra(AppConstance.PATH).toString()
            Log.e("TAG", "initView: $path", )
            if (path.contains("jpg")) play.beGone() else play.beVisible()
            Glide.with(this@VideoActivity)
                .load(path)
                .into(imgThumb)
        }
    }

    fun shareVideo(file: File) {
        val videoUri: Uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
        )
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "video/*"
            putExtra(Intent.EXTRA_STREAM, videoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Share video via"))
    }

    fun dialogDelete(){
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog).create()
        val binding = DialogDeleteBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        binding.apply {
            cancel.setOnClickListener {
                builder.dismiss()
            }

            delete.setOnClickListener {
                deleteVideo(path)
                builder.dismiss()
            }

        }

        builder.show()
    }

    private fun deleteVideo(path: String) {
        val file = File(path)
        if (!file.exists()) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
            return
        }

        val contentResolver = contentResolver
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val where = MediaStore.MediaColumns.DATA + "=?"
        val selectionArgs = arrayOf(file.absolutePath)

        val rowsDeleted = contentResolver.delete(uri, where, selectionArgs)

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
        }
    }


}