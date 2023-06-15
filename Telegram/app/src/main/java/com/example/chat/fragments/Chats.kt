package com.example.chat.fragments

import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.utilits.ACTIVITY

class Chats : Fragment(R.layout.chats) {

    override fun onResume() {
        super.onResume()
        ACTIVITY.title = "Чаты"
        ACTIVITY.mAppDrawer.enableDrawer()
    }

}