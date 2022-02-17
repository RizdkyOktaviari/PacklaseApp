package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.service.ServiceResponse

interface PaketContract {
    interface View: BaseView {
        fun onPaketSuccess(paketResponse: ServiceResponse)
        fun onPaketFailed(message:String)
    }

    interface Presenter : PaketContract, BasePresenter {
        fun getPaket()
    }
}