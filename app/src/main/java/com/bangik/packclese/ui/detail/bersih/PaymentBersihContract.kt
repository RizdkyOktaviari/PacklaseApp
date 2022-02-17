package com.bangik.packclese.ui.detail.bersih

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.checkout.CheckoutBersihResponse

interface PaymentBersihContract {
    interface View: BaseView{
        fun onCheckoutSuccess(checkoutBersihResponse: CheckoutBersihResponse, view: android.view.View)
        fun onCheckoutFailed(message:String)
    }

    interface Presenter : PaymentBersihContract, BasePresenter{
        fun getCheckout(userId:String, total:String, serviceId:String, address:String, space:String, paymenMethod:String, view:android.view.View)
    }
}