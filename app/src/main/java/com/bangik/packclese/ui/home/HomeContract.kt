package com.bangik.packclese.ui.home

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.home.HomeResponse

interface HomeContract {
    interface View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message:String)

    }

    interface Presenter : HomeContract, BasePresenter {
        fun getHome()
    }
}