package com.bangik.packclese.ui.detail.laundry

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.service.ServiceResponse

interface LaundryContract {
    interface View: BaseView {
        fun onLaundrySuccess(laundryResponse: ServiceResponse)
        fun onLaundryFailed(message:String)
    }

    interface Presenter : LaundryContract, BasePresenter {
        fun getLaundry()
    }
}