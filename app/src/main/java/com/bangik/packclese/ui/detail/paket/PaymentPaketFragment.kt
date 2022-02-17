package com.bangik.packclese.ui.detail.paket

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bangik.packclese.Packclese
import com.bangik.packclese.R
import com.bangik.packclese.model.response.checkout.CheckoutPaketResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_payment_paket.*

class PaymentPaketFragment : Fragment(), PaymentPaketContract.View {

    var progressDialog: Dialog?= null
    lateinit var presenter: PaymentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_paket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var data = arguments?.getParcelable<DataPaket>("dataPaket")
        initView(data)
        initViews()
        presenter = PaymentPresenter(this)
    }

    private fun initView(data: DataPaket?) {
        Glide.with(requireContext())
            .load(data?.picturePaket)
            .into(ivPoster)

        tvTitle.text = data?.jenisPaket
        tvPrice.formatPrice(data?.price.toString())
        textView14.text = data?.weight + " gram"
        tvHargaOngkir.formatPrice(data?.ongkir.toString())
        tvHarga.formatPrice(data?.price.toString())
        tvTotal.formatPrice(data?.total.toString())

        tvOrigin.text = data?.origin
        tvDestination.text = data?.destination
        tvWeight.text = data?.weight + " gram"
        tvKurir.text = data?.courier
        tvEtd.text = data?.etd + " Hari"

        var user = Packclese.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        tvName.text = userResponse.name
        tvPhoneNo.text = userResponse.phoneNumber
        tvEmail.text = userResponse.email

        btnCheckout.setOnClickListener {
            var paymentMethod: String
            if (radioBtn1.isChecked){
                paymentMethod = "0"
            }else{
                paymentMethod = "1"
            }

            presenter.getCheckout(
                userResponse?.id.toString(),
                data?.total.toString(),
                data?.idPaket.toString(),
                data?.address.toString(),
                data?.origin.toString(),
                data?.destination.toString(),
                data?.weight.toString(),
                data?.courier.toString(),
                data?.ongkir.toString(),
                paymentMethod,
                it
            )
        }

    }

    private fun initViews(){
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onCheckoutSuccess(checkoutPaketResponse: CheckoutPaketResponse, view: View) {
        if(!checkoutPaketResponse.payment_url.equals("COD")){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(checkoutPaketResponse.payment_url)
            startActivity(i)
        }
        Navigation.findNavController(view).navigate(R.id.action_paymentPaketFragment_to_paymentPaketSuccessFragment)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}