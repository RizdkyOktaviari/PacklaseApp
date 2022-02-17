package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CekongkirPresenter(private val view:CekongkirContract.View): CekongkirContract.presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun cekOngkir(origin: String, destination: String, weight: String, courier: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.cekOngkir(origin, destination,weight,courier)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onCekongkirSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onCekongkirFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCekongkirFailed(it.message.toString())
                })
        mCompositeDisposable!!.add(disposable)

    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}