package com.example.chat.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.example.chat.R
import com.example.chat.utilits.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*


class ChangeUsername : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var username: EditText
    lateinit var mNewUsername: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_username, container, false)
        username = view.findViewById(R.id.username)
        return view
    }
    override fun onResume() {
        super.onResume()
        username.setText(USER.username)
    }

    override fun change() {
        mNewUsername = username.text.toString().toLowerCase(Locale.getDefault())
        if(mNewUsername.isEmpty()){
            Toast.makeText(ACTIVITY, "Логин не должен быть пустым!", Toast.LENGTH_SHORT).show()
        }else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent( object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.hasChild(mNewUsername)){
                            Toast.makeText(ACTIVITY, "Такой логин уже занят!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            changeUsername()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    updateCurrentUsername(mNewUsername)
                }
            }
    }

}