package com.bangik.packclese.ui.detail.bersih

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BersihPresenter(private val view: BersihContract.View) : BersihContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getBersih() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.bersih()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onBersihSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onBersihFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onBersihFailed(it.message.toString())
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