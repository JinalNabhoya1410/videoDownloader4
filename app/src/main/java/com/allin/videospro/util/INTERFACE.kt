package com.allin.videospro.util

import com.allin.videospro.ui.model.Language
import com.allin.videospro.ui.model.StatusModel


interface OnLangClick{
    fun onClick(list: Language)
}

interface OnFolderClick {
    fun onClick(position:Int,list: ArrayList<String>)
}

interface OnDownloadClick {
    fun onClick(list:ArrayList<StatusModel>, position: Int)
}

interface OnVideoClick {
    fun onClick(path:String)
}