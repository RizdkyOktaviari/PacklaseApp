package com.bangik.packclese.ui.profile.account

import android.net.Uri
import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.model.response.login.UploadPhotoResponse
import com.bangik.packclese.model.response.profile.ProfileEditResponse

interface EditContract {

    interface View {

        fun onEditSuccess(ProfileEditResponse: ProfileEditResponse, view: View)
        fun onEditFailed(message:String)
        fun onEditPhotoSuccess(uploadPhotoResponse: UploadPhotoResponse, view: View)

    }

    interface Presenter : EditContract, BasePresenter {


        fun subimEdit(id:String, name:String, email:String, address:String, phoneNumber: String,photo:Uri?=null)
        fun subimPhoto(filePath: Uri, view: View)

    }
}

