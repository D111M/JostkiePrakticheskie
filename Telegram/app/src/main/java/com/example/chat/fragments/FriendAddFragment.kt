package com.example.chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.chat.R
import com.example.chat.utilits.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FriendAddFragment : BaseFragment(R.layout.fragment_friend_add) {

    lateinit var username: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friend_add, container, false)
        username = view.findViewById(R.id.login_add_friend)
        return view
    }

    override fun onResume() {
        super.onResume()
        initFields()
    }

    private fun initFields() {
        var array = arrayListOf<String>()
        username.addTextChangedListener(AppTextWatcher{
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.hasChild(username.text.toString())){
                            if(username.text.toString() != USER.username){
                                snapshot.children.forEach{ snapshot1->
                                    if(snapshot1.key.toString() == username.text.toString()){
                                        array.add(snapshot1.value.toString())
                                    }
                                }
                                REF_DATABASE_ROOT.child(NODE_FRIENDS).child(CURRENT_UID)
                                    .child(array[0])
                                    .child(CHILD_ID)
                                    .setValue(array[0])
                                REF_DATABASE_ROOT.child(NODE_FRIENDS).child(array[0])
                                    .child(CURRENT_UID)
                                    .child(CHILD_ID)
                                    .setValue(CURRENT_UID)
                                    .addOnSuccessListener {
                                        Toast.makeText(ACTIVITY, "Друг добавлен!", Toast.LENGTH_SHORT).show()
                                    }
                            }
                            else{
                                Toast.makeText(ACTIVITY, "Нельзя добавить самого себя!", Toast.LENGTH_SHORT).show()
                            }

                        }
                        else{
                            Toast.makeText(ACTIVITY, "Такого пользователя не существует", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        })
    }
}