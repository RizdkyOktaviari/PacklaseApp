package com.bangik.packclese.ui.detail.titip

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.checkout.CheckoutTitipResponse

interface PaymentTitipContract {
    interface View: BaseView {
        fun onCheckoutSuccess(checkoutTitipResponse: CheckoutTitipResponse, view: android.view.View)
        fun onCheckoutFailed(message:String)
    }

    interface Presenter : PaymentTitipContract, BasePresenter{
        fun getCheckout(userId:String, total:String, serviceId:String, address:String, start:String, end:String, quantity:String, paymenMethod:String, view:android.view.View)
    }
}