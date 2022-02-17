package com.bangik.packclese.ui.detail.laundry

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bangik.packclese.R
import com.bangik.packclese.model.response.home.HomeResponse
import com.bangik.packclese.model.response.service.ServiceResponse
import com.bangik.packclese.ui.home.HomeContract
import com.bangik.packclese.ui.home.HomePresenter
import com.bangik.packclese.utils.Helpers.append
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_bersih.*
import kotlinx.android.synthetic.main.fragment_detail_laundry.*
import kotlinx.android.synthetic.main.fragment_detail_laundry.btnOrderNow
import kotlinx.android.synthetic.main.fragment_detail_laundry.ivPoster
import kotlinx.android.synthetic.main.fragment_detail_laundry.ratingBar
import kotlinx.android.synthetic.main.fragment_detail_laundry.tvDesc
import kotlinx.android.synthetic.main.fragment_detail_laundry.tvTitle
import kotlinx.android.synthetic.main.fragment_detail_laundry.tvTotal

class DetailLaundryFragment : Fragment(), LaundryContract.View, HomeContract.View{

    private lateinit var presenter: LaundryPresenter
    private lateinit var presenterHome: HomePresenter
    var progressDialog : Dialog? = null
    private  var  price: Int = 0
    private var tambahan: Int = 0
    private  var berat: Int = 0
    private lateinit var jenisLaundry: String
    private lateinit var pictureLaundry: String
    private lateinit var idLaundry: String
    var bundle:Bundle ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_laundry, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailLaundryActivity).toolbarDetail()

        initView()
        presenter = LaundryPresenter(this)
        presenter.getLaundry()

        presenterHome = HomePresenter(this)
        presenterHome.getHome()

        btnOrderNow.setOnClickListener {
            if (radioBtn1.isChecked){
                tambahan = 2000
            }else{
                tambahan = 0
            }

            if (etBerat.text.isNullOrEmpty()){
                etBerat.error = "Berat tidak boleh kosong"
                etBerat.requestFocus()
            }else if(price.equals(0)){
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
            else{
                berat = etBerat.text.toString().toInt()
                val dataLaundry = DataLaundry(price, tambahan, berat, jenisLaundry, pictureLaundry, idLaundry)
                bundle = bundleOf("data" to dataLaundry)
                Navigation.findNavController(it).navigate(R.id.action_payment_laundry, bundle)
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

    override fun onLaundrySuccess(laundryResponse: ServiceResponse) {
        var name = arrayOf<String>()
        for(i in laundryResponse.data.indices){
            name = append(name, laundryResponse.data[i].name)
        }
        spinerLaundry.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }

        spinerLaundry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in laundryResponse.data.indices){
                    if (laundryResponse.data[i].name.equals(spinerLaundry.selectedItem.toString())){
                        price = laundryResponse.data[i].price
                        jenisLaundry = laundryResponse.data[i].name
                        idLaundry = laundryResponse.data[i].id.toString()
                    }
                }

                tvTotal.formatPrice(price.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onLaundryFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Glide.with(requireContext())
            .load(homeResponse.data[0].picturePath)
            .into(ivPoster)
        tvTitle.text = homeResponse.data[0].jenis
        tvDesc.text = homeResponse.data[0].description
        ratingBar.rating = homeResponse.data[0].rate
        pictureLaundry = homeResponse.data[0].picturePath.toString()
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
