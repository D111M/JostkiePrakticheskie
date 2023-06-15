package com.example.chat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.chat.R
import com.example.chat.RegActivity
import com.example.chat.utilits.*
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment(val mPhone: String,val id: String) : Fragment() {

    lateinit var code: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_code, container, false)
        code = view.findViewById(R.id.code)
        return view
    }
    override fun onStart() {
        super.onStart()
        (activity as RegActivity).title = mPhone
        code.addTextChangedListener(AppTextWatcher{
            val string = code.text.toString()
            if(string.length == 6){
                enterCode()
            }
        })
    }

    private fun enterCode(){
        val Code = code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id,Code)
        AUTH.signInWithCredential(credential).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = mPhone

                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
                    .setValue(USER.username)
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnSuccessListener{
                                Toast.makeText(ACTIVITY, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                                restartActivity()
                            }
                    }

            }else
                Toast.makeText(ACTIVITY, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}