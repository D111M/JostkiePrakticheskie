package com.example.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.databinding.ActivityRegBinding
import com.example.chat.fragments.EnterCodeFragment
import com.example.chat.fragments.EnterPhoneFragment
import com.example.chat.utilits.initFirebase
import com.example.chat.utilits.replaceFragment

class RegActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.regToolBar
        setSupportActionBar(mToolbar)
        title = "Ваш телефон"
        replaceFragment(EnterPhoneFragment(), false)
    }
}