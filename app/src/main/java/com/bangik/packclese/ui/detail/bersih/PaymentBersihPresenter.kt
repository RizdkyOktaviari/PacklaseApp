package com.bangik.packclese.ui.detail.bersih

import android.view.View
import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentBersihPresenter(private val views: PaymentBersihContract.View) :PaymentBersihContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(
        userId: String,
        total: String,
        serviceId: String,
        address: String,
        space: String,
        paymenMethod: String,
        view: View
    ) {
        views.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.checkoutBersih(
            userId,
            total,
            serviceId,
            address,
            space,
            paymenMethod
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    views.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> views.onCheckoutSuccess(it1, view) }
                    } else {
                        it.meta?.message?.let { it1 -> views.onCheckoutFailed(it1) }
                    }
                },
                {
                    views.dismissLoading()
                    views.onCheckoutFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}