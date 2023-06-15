package com.example.chat.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.utilits.ACTIVITY


open class BaseChangeFragment(layout:Int ) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        ACTIVITY.mAppDrawer.disableDrawer()
        ACTIVITY.hideKeyboard()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        ACTIVITY.menuInflater.inflate(R.menu.menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}