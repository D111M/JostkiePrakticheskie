package com.example.chat.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.models.Model
import com.example.chat.utilits.*

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.SingleChatHolder>() {

    private var listMessageCache = emptyList<Model>()
    private lateinit var mDifResult:DiffUtil.DiffResult

    class SingleChatHolder(view: View): RecyclerView.ViewHolder(view){
        val blockUserMessage: ConstraintLayout = view.findViewById(R.id.block_user_message)
        val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
        val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_time_message)

        val blockReceivedMessage: ConstraintLayout = view.findViewById(R.id.block_user_received_message)
        val chatReceivedMessage: TextView = view.findViewById(R.id.chat_user_received_message)
        val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_user_received_time_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent,false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if(listMessageCache[position].from == CURRENT_UID){
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = listMessageCache[position].text
            holder.chatUserMessageTime.text = listMessageCache[position].timeStamp.toString().asTime()
            holder.blockUserMessage.setOnClickListener {

            }
        }
        else{
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = listMessageCache[position].text
            holder.chatReceivedMessageTime.text = listMessageCache[position].timeStamp.toString().asTime()
        }

    }

    override fun getItemCount(): Int = listMessageCache.size

    fun addItem(item: Model){
        val newList = mutableListOf<Model>()
        newList.addAll(listMessageCache)
        newList.add(item)
        mDifResult = DiffUtil.calculateDiff(Callback(listMessageCache, newList))
        mDifResult.dispatchUpdatesTo(this)
        listMessageCache = newList
    }

}


