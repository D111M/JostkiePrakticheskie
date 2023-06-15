package com.example.chat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.models.Model
import com.example.chat.models.User
import com.example.chat.utilits.*
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ChatFragment(private val contact: Model) : BaseFragment(R.layout.fragment_chat) {

    private lateinit var mListenerInfoToolbar: ValueEventListener
    private lateinit var mReceivingUser: User
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var  mRefMessages: DatabaseReference
    private lateinit var mAdapter: ChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: ChildEventListener
    private var mListMessages = mutableListOf<Model>()

    lateinit var message: EditText
    lateinit var send: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        mRecyclerView = view.findViewById(R.id.chatRecycler)
        message = view.findViewById(R.id.sendChatMessage)
        send = view.findViewById(R.id.chat_btn_send)
        return view
    }
    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = ChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.adapter = mAdapter
        mMessagesListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mAdapter.addItem(snapshot.getCommonModel())
                mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mRefMessages.addChildEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolbarInfo = ACTIVITY.mToolInfo
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mReceivingUser = snapshot.getUserModel()
                initInfoToolbar()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        send.setOnClickListener {
            val Message = message.text.toString()
            if (Message.isEmpty()) {
                Toast.makeText(ACTIVITY, "Введите сообщение!", Toast.LENGTH_SHORT).show()
            } else sendMessage(Message, contact.id, TYPE_TEXT, contact.photoUrl) {
                message.setText("")
            }

        }
    }

    private fun initInfoToolbar() {
        Picasso.get()
            .load(mReceivingUser.photoUrl)
            .fit()
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(ACTIVITY.icon)
        ACTIVITY.fullname.text = mReceivingUser.fullname
        ACTIVITY.status.text = mReceivingUser.status

    }

    override fun onPause() {
        super.onPause()
        ACTIVITY.mToolInfo.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }
}