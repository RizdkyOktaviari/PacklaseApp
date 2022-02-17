package com.bangik.packclese.ui.detail.bersih

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bangik.packclese.R
import com.bangik.packclese.model.response.home.HomeResponse
import com.bangik.packclese.model.response.service.ServiceResponse
import com.bangik.packclese.ui.home.HomeContract
import com.bangik.packclese.ui.home.HomePresenter
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_bersih.*

class DetailBersihFragment : Fragment(), BersihContract.View, HomeContract.View {

    private lateinit var presenter: BersihPresenter
    private lateinit var presenterHome: HomePresenter
    var progressDialog : Dialog? = null
    private  var  price: Int = 0
    private  var space: Int = 0
    private lateinit var idBersih: String
    private lateinit var pictureBersih: String
    private lateinit var jenisBersih: String
    var bundle:Bundle ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_bersih, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailBersihActivity).toolbarDetail()

        initView()

        presenter = BersihPresenter(this)
        presenter.getBersih()

        presenterHome = HomePresenter(this)
        presenterHome.getHome()

        btnOrderNow.setOnClickListener {
            if (etPanjang.text.isNullOrEmpty()){
                etPanjang.error = "Panjang tidak boleh kosong"
                etPanjang.requestFocus()
            }else if(etLebar.text.isNullOrEmpty()){
                etLebar.error = "Panjang tidak boleh kosong"
                etLebar.requestFocus()
            }
            else{
                val panjang = etPanjang.text.toString().toInt()
                val lebar = etLebar.text.toString().toInt()
                space = panjang * lebar
                if (space.equals(0)){
                    Toast.makeText(context, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else{
                    val dataBersih = DataBersih(price, space, jenisBersih, pictureBersih, idBersih)
                    bundle = bundleOf("dataBersih" to dataBersih)
                    Navigation.findNavController(it).navigate(R.id.action_detailBersihFragment_to_paymentBersihFragment, bundle)
                }
            }
        }
    }

    private fun initView(){
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Glide.with(requireContext())
            .load(homeResponse.data[1].picturePath)
            .into(ivPoster)
        tvTitle.text = homeResponse.data[1].jenis.toString()
        tvDesc.text = homeResponse.data[1].description.toString()
        ratingBar.rating = homeResponse.data[1].rate
        jenisBersih = homeResponse.data[1].jenis.toString()
        pictureBersih = homeResponse.data[1].picturePath.toString()
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBersihSuccess(bersihResponse: ServiceResponse) {
        price = bersihResponse.data[0].price
        idBersih = bersihResponse.data[0].id.toString()
        tvTotal.formatPrice(bersihResponse.data[0].price.toString())
    }

    override fun onBersihFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}