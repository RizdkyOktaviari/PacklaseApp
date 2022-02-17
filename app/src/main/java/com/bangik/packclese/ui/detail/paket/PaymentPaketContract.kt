package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.checkout.CheckoutPaketResponse

interface PaymentPaketContract {
    interface View: BaseView {
        fun onCheckoutSuccess(checkoutPaketResponse: CheckoutPaketResponse, view: android.view.View)
        fun onCheckoutFailed(message:String)
    }

    interface Presenter : PaymentPaketContract, BasePresenter{
        fun getCheckout(userId:String, total:String, serviceId:String, address:String, origin:String, destination:String, weight:String, courier:String, ongkir:String, paymentMethod:String, view: android.view.View)
    }
}