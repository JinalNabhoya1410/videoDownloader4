package com.allin.videospro.ui.adapter

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allin.videospro.databinding.ItemMediaBinding
import com.allin.videospro.ui.model.StatusModel
import com.allin.videospro.util.OnDownloadClick
import com.allin.videospro.util.beGone
import com.allin.videospro.util.beVisible
import com.bumptech.glide.Glide

class WhatsappAdapter(var activity:Activity, var list:ArrayList<StatusModel>, var mediaType:String, var onClick: OnDownloadClick): RecyclerView.Adapter<WhatsappAdapter.ViewHolder>() {
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
                if (mediaType.equals("images")){
                    imgPlay.beGone()
                }else{
                    imgPlay.beVisible()
                }
                Glide.with(activity)
                    .load(Uri.parse(list[position].path))
                    .into(imgThumb)

                download.setOnClickListener {
                    onClick.onClick(list,position)
                }
            }
        }
    }

    fun updateList(newList: ArrayList<StatusModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}