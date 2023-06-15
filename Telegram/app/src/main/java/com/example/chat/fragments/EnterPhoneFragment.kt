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
import com.example.chat.utilits.ACTIVITY
import com.example.chat.utilits.AUTH
import com.example.chat.utilits.replaceFragment
import com.example.chat.utilits.restartActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment() {
    private lateinit var mPhone: String
    private lateinit var mCallBack:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var btn: FloatingActionButton
    lateinit var phone: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_phone, container, false)
        btn = view.findViewById(R.id.btn_next)
        phone = view.findViewById(R.id.reg_phone)
        return view
    }

    override fun onStart() {
        super.onStart()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Toast.makeText(ACTIVITY, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                        restartActivity()
                    }else Toast.makeText(ACTIVITY, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(ACTIVITY, p0.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhone, id))
            }
        }
        btn.setOnClickListener { sendCode() }
    }

    private fun sendCode(){
        if(phone.text.toString().isEmpty()){
            Toast.makeText(ACTIVITY, "Введите номер телефона", Toast.LENGTH_SHORT).show()
        }
        else{
            authUser()
        }
    }

    private fun authUser() {
        mPhone = phone.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhone,60,TimeUnit.SECONDS, activity as RegActivity, mCallBack
        )
    }
}