package com.bangik.packclese.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangik.packclese.Packclese
import com.bangik.packclese.R
import com.bangik.packclese.model.response.home.HomeResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.ui.detail.bersih.DetailBersihActivity
import com.bangik.packclese.ui.detail.laundry.DetailLaundryActivity
import com.bangik.packclese.ui.detail.paket.DetailPaketActivity
import com.bangik.packclese.ui.detail.titip.DetailTitipActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),HomeContract.View {

    private lateinit var presenter:HomePresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        presenter = HomePresenter(this)
        presenter.getHome()

        cardLaundry.setOnClickListener{
            val detail = Intent(activity, DetailLaundryActivity::class.java)
            startActivity(detail)
        }

        cardBersih.setOnClickListener{
            val detail = Intent(activity, DetailBersihActivity::class.java)
            startActivity(detail)
        }

        cardPaket.setOnClickListener{
            val detail = Intent(activity, DetailPaketActivity::class.java)
            startActivity(detail)
        }

        cardTitip.setOnClickListener{
            val detail = Intent(activity, DetailTitipActivity::class.java)
            startActivity(detail)
        }
    }

    fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        var user = Packclese.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        if (!userResponse.profile_photo_path.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load(userResponse.profile_photo_path)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Glide.with(requireActivity())
            .load(homeResponse.data.get(0).picturePath)
            .into(ivPoster1)
        Glide.with(requireActivity())
            .load(homeResponse.data.get(1).picturePath)
            .into(ivPoster2)
        Glide.with(requireActivity())
            .load(homeResponse.data.get(2).picturePath)
            .into(ivPoster3)
        Glide.with(requireActivity())
            .load(homeResponse.data.get(3).picturePath)
            .into(ivPoster4)

        tv1.text = homeResponse.data.get(0).jenis
        tv2.text = homeResponse.data.get(1).jenis
        tv3.text = homeResponse.data.get(2).jenis
        tv4.text = homeResponse.data.get(3).jenis

        rbService1.rating = homeResponse.data[0].rate
        rbService2.rating = homeResponse.data[1].rate
        rbService3.rating = homeResponse.data[2].rate
        rbService4.rating = homeResponse.data[3].rate
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}
