package com.bangik.packclese.ui.detail.titip

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.service.ServiceResponse

interface TitipContract {
    interface View: BaseView{
        fun onTitipSuccess(titipResponse: ServiceResponse)
        fun onTitipFailed(message:String)
    }

    interface Presenter : TitipContract, BasePresenter{
        fun getTitip()
    }
}