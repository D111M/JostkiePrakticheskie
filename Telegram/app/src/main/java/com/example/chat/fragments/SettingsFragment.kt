package com.example.chat.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chat.MainActivity
import com.example.chat.R
import com.example.chat.utilits.*
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView


@Suppress("DEPRECATION")
class SettingsFragment : BaseFragment(R.layout.fragment_profile) {

    lateinit var phone: TextView
    lateinit var username: TextView
    lateinit var bio: TextView
    lateinit var fullname: TextView
    lateinit var status: TextView
    lateinit var btn: ConstraintLayout
    lateinit var btnBio: ConstraintLayout
    lateinit var changeImage: CircleImageView
    lateinit var profileImage: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        phone = view.findViewById(R.id.phone_number)
        username = view.findViewById(R.id.login)
        bio = view.findViewById(R.id.biography)
        fullname = view.findViewById(R.id.profile_username)
        status = view.findViewById(R.id.status)
        btn = view.findViewById(R.id.profile_change_login)
        btnBio = view.findViewById(R.id.profile_change_bio)
        changeImage = view.findViewById(R.id.profile_change_image)
        profileImage = view.findViewById(R.id.profile_image)
        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings, menu)
    }
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        bio.text = USER.bio
        username.text = USER.username
        phone.text = USER.phone
        fullname.text = USER.fullname
        status.text = USER.status
        btn.setOnClickListener{ replaceFragment(ChangeUsername())}
        btnBio.setOnClickListener{ replaceFragment(ChangeBiography())}
        changeImage.setOnClickListener { changePhotoUser() }
        if(USER.photoUrl != null) {
            Picasso.get()
                .load(USER.photoUrl)
                .fit()
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(profileImage)
        }

    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1,1)
            .setRequestedSize(500,500)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(activity as MainActivity, this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOut -> {
                States.updateState(States.OFFLINE)
                AUTH.signOut()
                restartActivity()
            }
            R.id.change_name -> replaceFragment(ChangeFullname())
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)

            putImageToStorage(uri, path){
                getUrlFromStorage(path){
                    putUrlToDatabase(it){
                        Picasso.get()
                            .load(it)
                            .fit()
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .into(profileImage)

                        Toast.makeText(ACTIVITY, "Фото изменено!", Toast.LENGTH_SHORT).show()
                        USER.photoUrl = it
                        (activity as MainActivity).mAppDrawer.updateHeader()
                    }
                }
            }
        }

    }
}