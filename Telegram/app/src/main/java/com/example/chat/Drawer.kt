package com.example.chat

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.chat.fragments.FriendAddFragment
import com.example.chat.fragments.FriendsFragment
import com.example.chat.fragments.SettingsFragment
import com.example.chat.utilits.ACTIVITY
import com.example.chat.utilits.USER
import com.example.chat.utilits.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.Picasso

class Drawer() {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mCurrentProfile: ProfileDrawerItem

    fun create(){
        initLoader()
        createHeader()
        createDrawer()
        mDrawerLayout = mDrawer.drawerLayout
    }

    fun disableDrawer(){
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        ACTIVITY.mToolbar.setNavigationOnClickListener{
            ACTIVITY.supportFragmentManager.popBackStack()
        }
    }
    fun enableDrawer(){
        ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        ACTIVITY.mToolbar.setNavigationOnClickListener{
            mDrawer.openDrawer()
        }
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(ACTIVITY)
            .withToolbar(ACTIVITY.mToolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Друзья")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_person_24),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Добавить друга")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_person_add_24),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Профиль")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_baseline_settings_24)
            ).withOnDrawerItemClickListener(object :
                Drawer.OnDrawerItemClickListener.OnDrawerItemClickListener{
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>,
                ): Boolean {
                    clickToItem(position)
                    return false
                }
            }).build()
    }

    private fun clickToItem(position: Int){
        when(position){
            1 -> replaceFragment(FriendsFragment())
            2 -> replaceFragment(FriendAddFragment())
            4 -> replaceFragment(SettingsFragment())
        }
    }

    private fun createHeader() {
        if(USER.photoUrl != null){
            mCurrentProfile = ProfileDrawerItem()
                .withName(USER.fullname)
                .withEmail(USER.phone)
                .withIcon(USER.photoUrl)
                .withIdentifier(200)
        }
        else{
            mCurrentProfile = ProfileDrawerItem()
                .withName(USER.fullname)
                .withEmail(USER.phone)
                .withIdentifier(200)
        }
        mHeader = AccountHeaderBuilder()
            .withActivity(ACTIVITY)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                mCurrentProfile
            ).build()
    }

    fun updateHeader(){
        if(USER.photoUrl != null){
            mCurrentProfile
                .withName(USER.fullname)
                .withEmail(USER.phone)
                .withIcon(USER.photoUrl)
            mHeader.updateProfile(mCurrentProfile)
        }
        else{
            mCurrentProfile
                .withName(USER.fullname)
                .withEmail(USER.phone)
            mHeader.updateProfile(mCurrentProfile)
        }
    }

    private fun initLoader(){
        DrawerImageLoader.init(object : AbstractDrawerImageLoader(){
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                Picasso.get()
                    .load(uri.toString())
                    .fit()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(imageView)
            }
        })
    }
}