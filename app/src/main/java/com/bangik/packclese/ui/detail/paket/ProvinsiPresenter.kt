package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProvinsiPresenter(private val view:ProvinsiContract.View): ProvinsiContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun getProvinsi() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.dataProvinsi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onProvinsiSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onProvinsiFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onProvinsiFailed(it.message.toString() + " dari throwable")
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