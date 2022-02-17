package com.bangik.packclese.ui.detail.titip

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
import com.bangik.packclese.model.response.checkout.CheckoutTitipResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_payment_titip.*

class PaymentTitipFragment : Fragment(), PaymentTitipContract.View {

    var progressDialog: Dialog?= null
    lateinit var presenter: PaymentTitipPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_titip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailTitipActivity).toolbarPayment()
        var data = arguments?.getParcelable<DataTitip>("dataTitip")
        initView(data)
        initViews()
        presenter = PaymentTitipPresenter(this)
    }

    private fun initView(data: DataTitip?) {
        Glide.with(requireContext())
            .load(data?.pictureTitip)
            .into(ivPoster)

        tvTitle.text = data?.jenisTitip
        tvNameItem.text = data?.jenisTitip
        tvPrice.formatPrice(data?.price.toString())
        textView14.text = data?.quantity + " items"
        tvHarga.formatPrice(data?.price.toString())
        tvTotal.formatPrice(data?.total.toString())

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
                data?.idTitip.toString(),
                userResponse?.address.toString(),
                data?.dateStart.toString(),
                data?.dateEnd.toString(),
                data?.quantity.toString(),
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

    override fun onCheckoutSuccess(checkoutTitipResponse: CheckoutTitipResponse, view: View) {
        if (!checkoutTitipResponse.payment_url.equals("COD")){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(checkoutTitipResponse.payment_url)
            startActivity(i)
        }

        Navigation.findNavController(view).navigate(R.id.action_paymentTitipFragment_to_paymentTitipSuccessFragment)
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