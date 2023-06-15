package com.example.chat.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.models.Model
import com.example.chat.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class FriendsFragment : BaseFragment(R.layout.fragment_friends) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Model,ContactsHolder>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference

    override fun onResume() {
        super.onResume()
        ACTIVITY.title = "Друзья"
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = requireView().findViewById(R.id.recycler_friends)
        mRefContacts = REF_DATABASE_ROOT.child(NODE_FRIENDS).child(CURRENT_UID)
        val options = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(mRefContacts, Model::class.java)
            .build()
        mAdapter = object: FirebaseRecyclerAdapter<Model, ContactsHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item,parent,false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: Model,
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                mRefUsers.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val contact = snapshot.getCommonModel()

                        holder.name.text = contact.fullname
                        holder.status.text = contact.status
                        Picasso.get()
                            .load(contact.photoUrl)
                            .fit()
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .into(holder.photo)
                        holder.itemView.setOnClickListener { replaceFragment(ChatFragment(contact)) }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
                holder.delete.setOnClickListener {
                    REF_DATABASE_ROOT.child(NODE_FRIENDS).child(CURRENT_UID).child(model.id).removeValue()
                    REF_DATABASE_ROOT.child(NODE_FRIENDS).child(model.id).child(CURRENT_UID).removeValue()
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(ACTIVITY, "Пользователь удален из друзей!", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(ACTIVITY, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class ContactsHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.friend_fullname)
        val status: TextView = view.findViewById(R.id.friend_status)
        val photo: CircleImageView = view.findViewById(R.id.friend_photo)
        val delete: Button = view.findViewById(R.id.deleteFriend)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
    }

}


