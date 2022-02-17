package com.bangik.packclese.ui.detail.bersih

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.service.ServiceResponse

interface BersihContract {
    interface View: BaseView {
        fun onBersihSuccess(bersihResponse: ServiceResponse)
        fun onBersihFailed(message:String)
    }

    interface Presenter : BersihContract, BasePresenter {
        fun getBersih()
    }
}