package com.bangik.packclese.ui.order

import com.bangik.packclese.base.BasePresenter
import com.bangik.packclese.base.BaseView
import com.bangik.packclese.model.response.transaction.TransactionResponse

interface OrderContract {
    interface View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message:String)

    }

    interface Presenter : OrderContract, BasePresenter {
        fun getTransaction()
    }
}