package com.allin.videospro.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allin.videospro.databinding.ItemFolderBinding
import com.allin.videospro.util.OnFolderClick

class FolderAdapter(var activity:Activity,var onClick: OnFolderClick): RecyclerView.Adapter<FolderAdapter.ViewHolder>() {
    var list = arrayListOf("Instagram","Face Book","Twitter","WhatsApp","Josh","TikTok","Share Chat","Moj")
    var selectedPosition = 0
    class ViewHolder (var binding: ItemFolderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFolderBinding.inflate(LayoutInflater.from(activity),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.apply {
                txtName.text = list[position]
                root.isSelected = (position == selectedPosition)

                root.setOnClickListener {
                    val previousSelected = selectedPosition
                    selectedPosition = position

                    notifyItemChanged(previousSelected)
                    notifyItemChanged(selectedPosition)

                    onClick.onClick(position, list)
                }
            }
        }
    }

    fun setSelectedFromViewPager(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        notifyItemChanged(previous)
        notifyItemChanged(selectedPosition)
    }

}