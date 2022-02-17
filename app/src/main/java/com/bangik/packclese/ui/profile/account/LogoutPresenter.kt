package com.bangik.packclese.ui.profile.account

import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LogoutPresenter(private val view: LogoutContract.View) :LogoutContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun logout(view: LogoutContract.View) {
        val disposable = HttpClient.getInstance().getApi()!!.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onLogoutSuccess(view)}
                    } else {
                        it.meta?.message?.let { it1 -> view.onLogoutFailed(it1) }
                    }
                },
                {

                    view.onLogoutFailed(it.message.toString())
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