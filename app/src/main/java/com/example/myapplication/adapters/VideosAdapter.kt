package com.example.myapplication.adapters

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.VideoItem


class VideosAdapter(private val mVideoItems: List<VideoItem>) :
    RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(com.example.myapplication.R.layout.scrollvideo_activity,parent,false)
        return VideosAdapter.VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.setVideoData(mVideoItems[position])
    }

    override fun getItemCount(): Int {
        return mVideoItems.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mVideoView: VideoView = itemView.findViewById(com.example.myapplication.R.id.videoview)
        val txtTitle: TextView = itemView.findViewById(com.example.myapplication.R.id.txtTitle)
        val txtDesc: TextView = itemView.findViewById(com.example.myapplication.R.id.txtDesc)
        val mProgressBar: ProgressBar = itemView.findViewById(com.example.myapplication.R.id.progressBar)
        fun setVideoData(videoItem: VideoItem) {

            mVideoView.setVideoPath(videoItem.videoURL)
            mVideoView.setOnPreparedListener { mp ->
                mProgressBar.visibility = View.GONE
                mp.start()
                val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
                val screenRatio = mVideoView.width / mVideoView.height
                    .toFloat()
                val scale = videoRatio / screenRatio
                if (scale >= 1f) {
                    mVideoView.scaleX = scale
                } else {
                    mVideoView.scaleY = 1f / scale
                }
            }
            mVideoView.setOnCompletionListener { mp -> mp.start() }
        }

        init {




        }
    }
}
