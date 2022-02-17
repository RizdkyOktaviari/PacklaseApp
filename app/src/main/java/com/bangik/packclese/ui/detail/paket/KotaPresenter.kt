package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KotaPresenter(private val view:KotaContract.View) : KotaContract.presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun getKota(id: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.datakota(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onKotaSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onKotaFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onKotaFailed(it.message.toString())
                })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}