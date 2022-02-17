package com.bangik.packclese.ui.detail.laundry

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.checkout.CheckoutLaundryResponse

interface PaymentLaundryContract {
    interface View: BaseView {
        fun onCheckoutSuccess(checkoutLaundryResponse: CheckoutLaundryResponse, view: android.view.View)
        fun onCheckoutFailed(message:String)

    }

    interface Presenter : PaymentLaundryContract, BasePresenter {
        fun getCheckout(userId:String, total:String, serviceId:String, address:String, weight:String, antar:String, paymentMethod:String, view: android.view.View)
    }
}