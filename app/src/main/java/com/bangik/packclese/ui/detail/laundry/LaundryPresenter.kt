package com.bangik.packclese.ui.detail.laundry

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LaundryPresenter(private val view: LaundryContract.View) : LaundryContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getLaundry() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.laundry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onLaundrySuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onLaundryFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLaundryFailed(it.message.toString() + " dari throwable")
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