package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.adapters.VideosAdapter
import com.example.myapplication.data.VideoItem


import com.google.firebase.auth.FirebaseAuth


class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var id_email: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var profile: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth= FirebaseAuth.getInstance()


        val currentuser = mAuth.currentUser

        profile = view.findViewById(R.id.profile)

        id_email= view.findViewById(R.id.id_email)
        id_email.text = currentuser?.email

        Glide.with(this)
            .load(currentuser?.photoUrl)
            .circleCrop()
            .into(profile)






        val videosViewPager: ViewPager2 = view.findViewById(R.id.viewPagerVideos)
        val videoItems: MutableList<VideoItem> = ArrayList()

        val item = VideoItem()
        item.videoURL = "https://liciolentimo.co.ke/img/videos/facebook.mp4"
        item.videoTitle = "Women In Tech"
        item.videoDesc = "International Women's Day 2019"
        videoItems.add(item)

        val item2 = VideoItem()
        item2.videoURL = "https://liciolentimo.co.ke/img/videos/facebook3.mp4"
        item2.videoTitle = "Sasha Solomon"
        item2.videoDesc = "How Sasha Solomon Became a Software Developer at Twitter"
        videoItems.add(item2)

        val item3 = VideoItem()
        item3.videoURL = "https://liciolentimo.co.ke/img/videos/facebook2.mp4"
        item3.videoTitle = "Happy Hour Wednesday"
        item3.videoDesc = " Depth-First Search Algorithm"
        videoItems.add(item3)

        videosViewPager.adapter = VideosAdapter(videoItems)


    }
}