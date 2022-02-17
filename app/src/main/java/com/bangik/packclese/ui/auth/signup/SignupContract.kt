package com.bangik.packclese.ui.auth.signup

import android.net.Uri
import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.request.RegisterRequest
import com.bangik.packclese.model.response.login.LoginResponse
import com.bangik.packclese.model.response.login.UploadPhotoResponse

interface SignupContract {
    interface View: BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse, view:android.view.View)
        fun onRegisterPhotoSuccess(uploadPhotoResponse: UploadPhotoResponse, view:android.view.View)
        fun onRegisterFailed(message:String)

    }

    interface Presenter : SignupContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view:android.view.View)
        fun submitPhotoRegister(filePath: Uri, view:android.view.View)
    }
}