package com.bangik.packclese.ui.detail.bersih

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
import com.bangik.packclese.model.response.checkout.CheckoutBersihResponse
import com.bangik.packclese.model.response.login.User
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_payment_bersih.*

class PaymentBersihFragment : Fragment(), PaymentBersihContract.View {

    var progressDialog: Dialog?= null
    lateinit var presenter: PaymentBersihPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_bersih, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailBersihActivity).toolbarPayment()

        var data = arguments?.getParcelable<DataBersih>("dataBersih")
        initView(data)
        initViews()
        presenter = PaymentBersihPresenter(this)

    }

    fun initView(data: DataBersih?){
        var tambahanLuas = 0
        Glide.with(requireContext())
            .load(data?.pictureBersih)
            .into(ivPoster)
        tvTitle.text = data?.jenisBersih.toString()
        tvPrice.formatPrice(data?.price.toString())
        textView14.text = data?.space.toString() + " m2"

        if (data?.space!! > 9){
            tambahanLuas = data?.space - 9
        }

        val total = data?.price + (tambahanLuas * 20000)
        tvHarga.formatPrice(total.toString())
        tvTotal.formatPrice(total.toString())

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
                total.toString(),
                data?.idBersih,
                userResponse?.address.toString(),
                data?.space.toString(),
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

    override fun onCheckoutSuccess(checkoutBersihResponse: CheckoutBersihResponse, view: View) {
        if(!checkoutBersihResponse.payment_url.equals("COD")){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(checkoutBersihResponse.payment_url)
            startActivity(i)
        }

        Navigation.findNavController(view).navigate(R.id.action_paymentBersihFragment_to_paymentBersihSuccessFragment)

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