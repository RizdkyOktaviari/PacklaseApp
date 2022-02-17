package com.bangik.packclese.ui.detail.titip

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TitipPresenter(private val view:TitipContract.View): TitipContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getTitip() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.titip()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onTitipSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onTitipFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onTitipFailed(it.message.toString())
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