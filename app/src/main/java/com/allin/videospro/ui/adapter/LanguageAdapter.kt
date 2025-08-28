package com.allin.videospro.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allin.videospro.R
import com.allin.videospro.databinding.ItemLanguageBinding
import com.allin.videospro.ui.model.Language
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.OnLangClick
import com.allin.videospro.util.TinyDB

class LanguageAdapter(var activity:Activity,var list:ArrayList<Language>,var onClick: OnLangClick): RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    var selectedPos = 0
    class ViewHolder (var binding: ItemLanguageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLanguageBinding.inflate(LayoutInflater.from(activity),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            icon.setImageResource(list[position].icon)
            txtName.text = list[position].name

            if (TinyDB(activity).getInt(AppConstance.SELECTED_LANG_POS) == position) {
                root.setBackgroundResource(R.drawable.bg_accent_border_5)
                rbSelector.setImageResource(R.drawable.rb_lang_select)
            } else {
                root.setBackgroundResource(R.drawable.bg_primary_border_5)
                rbSelector.setImageResource(R.drawable.rb_lang_unselect)
            }

            root.setOnClickListener {
                val previousSelectedPos = TinyDB(activity).getInt(AppConstance.SELECTED_LANG_POS)
                TinyDB(activity).putInt(AppConstance.SELECTED_LANG_POS, position)
                selectedPos = position
                onClick.onClick(list[position])

                notifyItemChanged(previousSelectedPos)
                notifyItemChanged(selectedPos)
            }
        }
    }
}