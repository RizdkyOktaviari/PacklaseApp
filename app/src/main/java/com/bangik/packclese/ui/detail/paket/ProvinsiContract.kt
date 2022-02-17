package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.rajaongkir.ProvinsiResponse

interface ProvinsiContract {
    interface View: BaseView {
        fun onProvinsiSuccess(provinsiResponse: ProvinsiResponse)
        fun onProvinsiFailed(message:String)
    }

    interface Presenter : ProvinsiContract, BasePresenter {
        fun getProvinsi()
    }
}