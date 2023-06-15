package com.example.chat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.fragments.Chats
import com.example.chat.fragments.EnterPhoneFragment
import com.example.chat.utilits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: Drawer
    lateinit var mToolbar: Toolbar
    lateinit var mToolInfo: ConstraintLayout
    lateinit var icon: CircleImageView
    lateinit var fullname: TextView
    lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ACTIVITY = this
        initFirebase()
        initUser{
            initFields()
            initFunc()
        }
    }


    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if(AUTH.currentUser == null){
            replaceFragment(EnterPhoneFragment(),false)
        } else{
            mAppDrawer.create()
            replaceFragment(Chats(), false)
        }
    }

    private fun initFields(){
        mToolbar = mBinding.mainToolBar
        mToolInfo = findViewById(R.id.toolbar_info)
        icon = findViewById(R.id.toolbar_image)
        fullname = findViewById(R.id.toolbar_fullname)
        status = findViewById(R.id.toolbar_status)
        mAppDrawer = Drawer()

    }

    fun hideKeyboard(){
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        States.updateState(States.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        States.updateState(States.OFFLINE)
    }
}