package com.bangik.packclese.ui.profile.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangik.packclese.Packclese
import com.bangik.packclese.R
import com.bangik.packclese.model.response.login.UploadPhotoResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.model.response.profile.ProfileEditResponse
import com.bangik.packclese.ui.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity(), EditContract.View {
    lateinit var id: String
    lateinit var presenter: EditPresenter
    var filePath: Uri?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        presenter = EditPresenter(this)


        initdummy()
        initListener()

        btnEditProfile.setOnClickListener(){
            var name = edt_username.text.toString()
            var email = edt_email.text.toString()
            var address = edt_address.text.toString()
            var phoneNumber = edt_phoneNumber.text.toString()
            var photo = filePath

            if (email.isNullOrEmpty()) {
                edt_email.error = "Silahkan masukkan Email Anda"
                edt_email.requestFocus()

            } else if (name.isNullOrEmpty()) {
                edt_username.error = "Silahkan masukkan Username Anda"
                edt_username.requestFocus()
            }else if (address.isNullOrEmpty()){
                edt_username.error = "Silahkan masukkan Username Anda"
                edt_username.requestFocus()
            }else if (phoneNumber.isNullOrEmpty()){
                edt_username.error = "Silahkan masukkan Username Anda"
                edt_username.requestFocus()
            }
            else {
                presenter.subimEdit(id, name, email, address, phoneNumber,photo )
            }
        }
    }


    override fun onEditSuccess(ProfileEditResponse: ProfileEditResponse,view: EditContract.View) {
        val gson = Gson()
        val json = gson.toJson(ProfileEditResponse.user)
        Packclese.getApp().setUser(json)

        if (filePath == null) {
            val home = Intent(this, MainActivity::class.java)
            startActivity(home)
            finish()

        } else {
            Log.v("tamvan&berani", Packclese.getApp().getToken())
            presenter.subimPhoto(filePath!!, view)
            val home = Intent(this, MainActivity::class.java)
            startActivity(home)
            finish()
        }
    }


    override fun onEditFailed(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onEditPhotoSuccess(
        uploadPhotoResponse: UploadPhotoResponse,
        view: EditContract.View
    ) {
        val gson = Gson()
        val json = gson.toJson(uploadPhotoResponse.user)
        Packclese.getApp().setUser(json)

        Toast.makeText(applicationContext, "Data berhasil dirubah", Toast.LENGTH_SHORT).show()
    }




    fun initdummy() {
        var user = Packclese.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        if (userResponse.name.isNullOrEmpty()){
            Toast.makeText(applicationContext, "data telah diubah harap diupdate", Toast.LENGTH_LONG).show()
        }else{

            id = userResponse.id.toString()
            edt_username.setText(userResponse.name.toString())
            edt_email.setText(userResponse.email.toString())
            edt_address.setText(userResponse.address.toString())
            edt_phoneNumber.setText(userResponse.phoneNumber.toString())
        }

    }

    private fun initListener() {
        EditProfil.setOnClickListener {
            ImagePicker.with(this)
                    .compress(2048)
                    .start()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data

            Glide.with(this)
                    .load(filePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(EditProfil)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}