package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.rajaongkir.CekongkirResponse

interface CekongkirContract {
    interface  View: BaseView {
        fun onCekongkirSuccess(cekongkirResponse: CekongkirResponse)
        fun onCekongkirFailed(message:String)
    }

    interface presenter: CekongkirContract, BasePresenter{
        fun cekOngkir(origin:String, destination:String, weight:String, courier:String)
    }
}