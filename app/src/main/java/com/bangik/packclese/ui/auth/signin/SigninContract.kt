package com.bangik.packclese.ui.auth.signin

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.login.LoginResponse

interface SigninContract {
    interface View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message:String)

    }

    interface Presenter : SigninContract, BasePresenter {
        fun subimtLogin(email:String, password:String)
    }
}