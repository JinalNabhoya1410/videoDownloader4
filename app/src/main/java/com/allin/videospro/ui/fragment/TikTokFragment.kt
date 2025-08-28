package com.allin.videospro.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.allin.videospro.databinding.FragmentTikTokBinding
import com.allin.videospro.ui.activity.VideoActivity
import com.allin.videospro.ui.adapter.DownloadedVideoAdapter
import com.allin.videospro.util.AppConstance
import com.allin.videospro.util.OnVideoClick
import com.allin.videospro.util.beGone
import com.allin.videospro.util.beVisible
import com.allin.videospro.util.getVideos
import java.io.File

class TikTokFragment : Fragment() {
    private lateinit var binding: FragmentTikTokBinding
    var list: List<File> = listOf()
    var adapter: DownloadedVideoAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTikTokBinding.inflate(layoutInflater)
        initView()
        initAdapter()
        return binding.root
    }
    private fun initView() {
        binding.apply {
            list = requireActivity().getVideos(AppConstance.TIKTOK)
            if (list.isEmpty())  layoutNoData.beVisible() else layoutNoData.beGone()
        }
    }

    private fun initAdapter() {
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireActivity(),3)
            adapter = DownloadedVideoAdapter(requireActivity(),list,object : OnVideoClick {
                override fun onClick(path: String) {
                    Intent(requireActivity(), VideoActivity::class.java).apply {
                        putExtra(AppConstance.PATH,path)
                        startActivity(this)
                    }
                }
            })
            recyclerview.adapter = adapter
        }
    }

    override fun onResume() {
        initView()
        initAdapter()
        super.onResume()
    }
}