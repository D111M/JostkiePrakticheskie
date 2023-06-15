package com.example.chat.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.example.chat.R
import com.example.chat.utilits.*

class ChangeFullname : BaseChangeFragment(R.layout.fragment_change_fullname) {
    lateinit var Name: EditText
    lateinit var Surname: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_fullname, container, false)
        Name = view.findViewById(R.id.name)
        Surname = view.findViewById(R.id.surname)
        return view
    }
    override fun onResume() {
        super.onResume()
        initFullnames()
    }

    private fun initFullnames(){
        val fullnameList = USER.fullname.split(" ")
        if(fullnameList.size > 1){
            Name.setText(fullnameList[0])
            Surname.setText(fullnameList[1])
        }else Name.setText(fullnameList[0])
    }

    override fun change() {
        val name = Name.text.toString()
        val surname = Surname.text.toString()
        if(name.isEmpty() || surname.isEmpty()){
            Toast.makeText(ACTIVITY, "Имя и фамилия не должны быть пустыми!", Toast.LENGTH_SHORT).show()
        }
        else{
            val fullname = "$name $surname"
            setNameToDatabase(fullname)
        }
    }


}