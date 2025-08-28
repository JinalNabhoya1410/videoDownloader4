package com.allin.videospro.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allin.videospro.databinding.ItemMediaBinding
import com.allin.videospro.util.OnVideoClick
import com.allin.videospro.util.beGone
import com.allin.videospro.util.beVisible
import com.bumptech.glide.Glide
import java.io.File

class DownloadedVideoAdapter(var activity:Activity,var list:List<File>,var onClick: OnVideoClick):RecyclerView.Adapter<DownloadedVideoAdapter.ViewHolder>(){
    class ViewHolder (var binding: ItemMediaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(activity),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                if (list[position].absolutePath.contains("jpg")) imgPlay.beGone() else imgPlay.beVisible()
                download.beGone()
                Glide.with(activity)
                    .load(list[position].absolutePath)
                    .into(imgThumb)

                root.setOnClickListener {
                    onClick.onClick(list[position].absolutePath)
                }
            }
        }
    }
}