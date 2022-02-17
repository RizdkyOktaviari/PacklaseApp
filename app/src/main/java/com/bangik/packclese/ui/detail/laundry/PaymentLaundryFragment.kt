package com.bangik.packclese.ui.detail.laundry

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
import com.bangik.packclese.model.response.checkout.CheckoutLaundryResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_payment_laundry.*

class PaymentLaundryFragment : Fragment(), PaymentLaundryContract.View {

    var progressDialog: Dialog?= null
    lateinit var presenter: PaymentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_laundry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailLaundryActivity).toolbarPayment()

        var data = arguments?.getParcelable<DataLaundry>("data")
        initView(data)
        initViews()
        presenter = PaymentPresenter(this)
    }

    fun initView(data: DataLaundry?){
        Glide.with(requireContext())
            .load(data?.pictureLaundry)
            .into(ivPoster)

        if (data?.jenisLaundry.toString().count() >= 20){
            tvTitle.text = data?.jenisLaundry.toString().dropLast(15) + "..."
        }else{
            tvTitle.text = data?.jenisLaundry.toString()
        }

        tvPrice.formatPrice(data?.price.toString())
        textView16.formatPrice(data?.tambahan.toString())
        textView14.text = data?.berat.toString() + " Kg"

        val subTotal = (data?.price!! * data?.berat!!)
        val total = (data?.price!! * data?.berat!!) + data?.tambahan!!

        var user = Packclese.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        tvName.text = userResponse.name
        tvPhoneNo.text = userResponse.phoneNumber
        tvEmail.text = userResponse.email

        tvHarga.formatPrice(subTotal.toString())
        tvTotal.formatPrice(total.toString())

        btnCheckout.setOnClickListener {
            var paymentMethod: String
            if (radioBtn1.isChecked){
                paymentMethod = "0"
            }else{
                paymentMethod = "1"
            }
            presenter.getCheckout(
                userResponse?.id.toString(),
                total.toString(),
                data?.idLaundry,
                userResponse?.address.toString(),
                data?.berat.toString(),
                data?.tambahan.toString(),
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

    override fun onCheckoutSuccess(checkoutLaundryResponse: CheckoutLaundryResponse, view: View) {
        if(!checkoutLaundryResponse.payment_url.equals("COD")){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(checkoutLaundryResponse.payment_url)
            startActivity(i)
        }

        Navigation.findNavController(view).navigate(R.id.action_fragmentPaymentLaundry_to_fragmentPaymentSuccess)
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