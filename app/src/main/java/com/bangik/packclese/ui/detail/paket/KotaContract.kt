package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.rajaongkir.KotaResponse

interface KotaContract {
    interface View: BaseView{
        fun onKotaSuccess(kotaResponse: KotaResponse)
        fun onKotaFailed(message:String)
    }

    interface presenter : KotaContract,BasePresenter{
        fun getKota(id:String)
    }
}