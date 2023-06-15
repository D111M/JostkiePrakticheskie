package com.example.chat.utilits

import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.chat.MainActivity
import com.example.chat.R
import java.text.SimpleDateFormat
import java.util.*

fun restartActivity(){
    val intent = Intent(ACTIVITY, MainActivity::class.java)
    ACTIVITY.startActivity(intent)
    ACTIVITY.finish()
}

fun replaceFragment(fragment: Fragment, addStack: Boolean = true){
    if(addStack){
        ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragment).commit()
    }
    else{
        ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).commit()
    }
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

