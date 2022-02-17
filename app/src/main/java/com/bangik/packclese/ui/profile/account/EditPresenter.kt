package com.bangik.packclese.ui.profile.account

import android.net.Uri
import com.bangik.packclese.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditPresenter(private val view: EditContract.View) : EditContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }


    override fun subimEdit(id: String, name: String, email: String, address: String, phoneNumber: String, photo: Uri?) {

        val disposable = HttpClient.getInstance().getApi()!!.edit(id,name,email,address,phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {


                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onEditSuccess(it1, view)}
                    } else {
                        it.meta?.message?.let { it1 -> view.onEditFailed(it1) }
                    }
                },
                {

                    view.onEditFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subimPhoto(filePath: Uri, viewParms: EditContract.View) {

        var profileImageFile = File(filePath.path)
        val profileImageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile)
        val profileImageParms = MultipartBody.Part.createFormData("file", profileImageFile.name, profileImageRequestBody)

        val disposable = HttpClient.getInstance().getApi()!!.registerPhoto(
            profileImageParms)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onEditPhotoSuccess(it1,viewParms) }
                    } else {
                        view.onEditFailed(it.meta?.message.toString())
                    }
                },
                {
                    view.onEditFailed(it.message.toString())
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