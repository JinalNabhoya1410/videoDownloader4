package com.allin.videospro.util

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import androidx.fragment.app.FragmentActivity
import com.allin.videospro.R
import com.allin.videospro.ui.model.Language
import com.allin.videospro.ui.model.StatusModel
import java.io.File

fun Activity.getLanguageList() : ArrayList<Language>{
    val list = ArrayList<Language>()
    list.add(Language(R.drawable.lang_en,"English","en"))
    list.add(Language(R.drawable.lang_hi,"Hindi","hi"))
    list.add(Language(R.drawable.lang_es,"Spanish","es"))
    list.add(Language(R.drawable.lang_pt,"Portuguese","pt"))
    list.add(Language(R.drawable.lang_fr,"French","fr"))
    list.add(Language(R.drawable.lang_de,"German","de"))
    list.add(Language(R.drawable.lang_ru,"Russian","ru"))
    list.add(Language(R.drawable.lang_ja,"Japanese","ja"))
    list.add(Language(R.drawable.lang_ko,"Korean","ko"))
    list.add(Language(R.drawable.lang_vi,"Vietnamese","vi"))
    return list
}

fun getRootDirPath(context: Context, folderName:String): String {
    val appName = context.getString(R.string.app_name)
    val folder = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        "$appName/$folderName"
    )
    if (!folder.exists()) folder.mkdirs()
    return folder.absolutePath
}

fun Activity.getWhatsappMedia(treeUri: Uri, mediaType: String): ArrayList<StatusModel> {
    val list = arrayListOf<StatusModel>()
    val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
        treeUri,
        DocumentsContract.getTreeDocumentId(treeUri)
    )

    contentResolver.query(
        childrenUri,
        arrayOf(
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_MIME_TYPE
        ),
        null,
        null,
        null
    )?.use { cursor ->
        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val documentId = cursor.getString(1)
            val mimeType = cursor.getString(2)

            if (mediaType == "images" && mimeType.startsWith("image/")) {
                val fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId)
                list.add(StatusModel(name, fileUri.toString()))
            } else if (mediaType == "videos" && mimeType.startsWith("video/")) {
                val fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId)
                list.add(StatusModel(name, fileUri.toString()))
            }
        }
    }
    return list
}

fun FragmentActivity.getVideos(folderName:String):List<File>{
    val appName = getString(R.string.app_name)
    val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$appName/$folderName")
    if (!folder.exists()) {
        return emptyList()
    }
    return folder.listFiles { file ->
        file.isFile && file.extension.equals("mp4", ignoreCase = true)
    }?.toList() ?: emptyList()
}

fun FragmentActivity.getWhatsappData(folderName:String):List<File>{
    val appName = getString(R.string.app_name)
    val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$appName/$folderName")
    if (!folder.exists()) {
        return emptyList()
    }
    return folder.listFiles { file ->
        file.isFile && file.extension.lowercase() in listOf("mp4", "jpg")
    }?.toList() ?: emptyList()
}