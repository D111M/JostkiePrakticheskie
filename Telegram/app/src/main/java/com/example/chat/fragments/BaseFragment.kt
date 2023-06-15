package com.example.chat.fragments

import androidx.fragment.app.Fragment
import com.example.chat.utilits.ACTIVITY

open class BaseFragment(val layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        ACTIVITY.mAppDrawer.disableDrawer()
    }

}