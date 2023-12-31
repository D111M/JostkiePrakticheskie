package com.example.chat.utilits

enum class States(val state: String) {
    ONLINE("В сети"),
    OFFLINE("Был недавно"),
    TYPING("Печатает...");

    companion object{
        fun updateState(appStates: States){
            if(AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                    .setValue(appStates.state)
                    .addOnSuccessListener { USER.status = appStates.state }
            }
        }
    }
}