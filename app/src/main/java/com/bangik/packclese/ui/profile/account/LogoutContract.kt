package com.bangik.packclese.ui.profile.account

import com.bangik.packclese.base.BasePresenter

interface LogoutContract {
    interface View{
        fun onLogoutSuccess(view:View)
        fun onLogoutFailed(message:String)
    }

    interface Presenter : LogoutContract, BasePresenter{
        fun logout(view: View)
    }
}