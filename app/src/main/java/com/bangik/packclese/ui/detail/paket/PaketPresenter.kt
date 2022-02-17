package com.bangik.packclese.ui.detail.paket

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaketPresenter(private val view:PaketContract.View) : PaketContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getPaket() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.paket()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onPaketSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onPaketFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onPaketFailed(it.message.toString())
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