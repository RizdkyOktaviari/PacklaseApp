package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KotaTujuanPresenter(private val view:KotaTujuanContract.View) : KotaTujuanContract.presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun getKotaTujuan(id: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.datakotaTujuan(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onKotaTujuanSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onKotaTujuanFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onKotaTujuanFailed(it.message.toString())
                })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}