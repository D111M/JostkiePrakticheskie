package com.example.chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.chat.R
import com.example.chat.utilits.*


class ChangeBiography : BaseChangeFragment(R.layout.fragment_change_biography) {
    lateinit var bio: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_biography, container, false)
        bio = view.findViewById(R.id.biography)
        return view
    }
    override fun onResume() {
        super.onResume()
        bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = bio.text.toString()
        setBioToDatabase(newBio)
    }

}