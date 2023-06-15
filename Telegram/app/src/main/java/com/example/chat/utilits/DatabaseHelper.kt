package com.example.chat.utilits

import android.net.Uri
import android.widget.Toast
import com.example.chat.models.Model
import com.example.chat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH:FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT:DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val TYPE_TEXT = "text"

const val FOLDER_PROFILE_IMAGE = "profile_image"
const val NODE_USERS = "users"
const val NODE_MESSAGES = "messages"
const val NODE_USERNAMES = "usernames"
const val NODE_FRIENDS = "friends"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_TIMESTAMP = "timeStamp"



const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATE = "status"



fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
}

inline fun getUrlFromStorage(path: StorageReference,crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
}

inline fun putImageToStorage(uri: Uri, path: StorageReference,crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
}
inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                USER = snapshot.getValue(User::class.java) ?:User()
                if(USER.username.isEmpty()){
                    USER.username = CURRENT_UID
                }
                function()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
}

fun sendMessage(message: String, receivingUserId: String, typeText: String, url: String, function: () -> Unit) {
    var refDialogUser = "$NODE_MESSAGES/$CURRENT_UID/$receivingUserId"
    var refDialogReceivingUser = "$NODE_MESSAGES/$receivingUserId/$CURRENT_UID"
    val messageKey = REF_DATABASE_ROOT.child(refDialogUser).push().key

    val mapMessage = hashMapOf<String, Any>()
    mapMessage[CHILD_FROM] = CURRENT_UID
    mapMessage[CHILD_TYPE] = typeText
    mapMessage[CHILD_TEXT] = message
    mapMessage[CHILD_PHOTO_URL] = url
    mapMessage[CHILD_TIMESTAMP] = ServerValue.TIMESTAMP

    val mapDialog = hashMapOf<String, Any>()
    mapDialog["$refDialogUser/$messageKey"] = mapMessage
    mapDialog["$refDialogReceivingUser/$messageKey"] = mapMessage

    REF_DATABASE_ROOT
        .updateChildren(mapDialog)
        .addOnSuccessListener { function() }
        .addOnFailureListener {
            Toast.makeText(ACTIVITY, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
}


fun updateCurrentUsername(mNewUsername: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
        .setValue(mNewUsername)
        .addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(ACTIVITY, "Логин обновлен!", Toast.LENGTH_SHORT).show()
                deleteOldUsername(mNewUsername)
            } else
                Toast.makeText(ACTIVITY, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
        }
}


fun deleteOldUsername(newUserName:String) {
    REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
        .addOnCompleteListener {
            if(it.isSuccessful){
                ACTIVITY.supportFragmentManager.popBackStack()
                USER.username = newUserName
            }
            else{
                Toast.makeText(ACTIVITY, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
}

fun setBioToDatabase(newBio: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO)
        .setValue(newBio).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(ACTIVITY, "Информация о себе была изменена!", Toast.LENGTH_SHORT).show()
                USER.bio = newBio
                 ACTIVITY.supportFragmentManager.popBackStack()
            }
        }
}

fun setNameToDatabase(fullname: String) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
        .setValue(fullname).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(ACTIVITY, "Фамилия и имя были успешно изменены", Toast.LENGTH_SHORT).show()
                USER.fullname = fullname
                ACTIVITY.supportFragmentManager?.popBackStack()
                ACTIVITY.mAppDrawer.updateHeader()
            }
        }
}

fun DataSnapshot.getCommonModel(): Model =
    this.getValue(Model::class.java)?: Model()

fun DataSnapshot.getUserModel(): User =
    this.getValue(User::class.java)?: User()
