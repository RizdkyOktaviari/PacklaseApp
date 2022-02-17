package com.bangik.packclese.ui.auth.signup

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bangik.packclese.Packclese
import com.bangik.packclese.R
import com.bangik.packclese.model.request.RegisterRequest
import com.bangik.packclese.model.response.login.LoginResponse
import com.bangik.packclese.model.response.login.UploadPhotoResponse
import com.bangik.packclese.ui.auth.AuthActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_signup_address.*

class SignupAddressFragment : Fragment(), SignupContract.View  {

    private lateinit var data: RegisterRequest
    lateinit var presenter: SignupPresenter
    var progressDialog: Dialog?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = SignupPresenter(this)
        data = arguments?.getParcelable<RegisterRequest>("data")!!

        initListener()
        initView()
        initDummy()
    }

    private fun initListener() {

        btnSignUpNow.setOnClickListener {

            var phone = etPhoneNumber.text.toString()
            var houseNumber = etHouseNumber.text.toString()
            var city = etCity.text.toString()
            var address = etAddress.text.toString() + ", " + houseNumber + ", " + city

            data.let {
                it.address = address
                it.phoneNumber = phone
            }

            if (phone.isNullOrEmpty()) {
                etPhoneNumber.error = "Silahkan masukkan nomor phone"
                etPhoneNumber.requestFocus()
            } else if (address.isNullOrEmpty()) {
                etAddress.error = "Silahkan masukkan address"
                etAddress.requestFocus()
            } else if (houseNumber.isNullOrEmpty()) {
                etHouseNumber.error = "Silahkan masukkan house number"
                etHouseNumber.requestFocus()
            } else if (city.isNullOrEmpty()) {
                etCity.error = "Silahkan masukkan city"
                etCity.requestFocus()
            } else {
                presenter.submitRegister(data, it)
            }


        }
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {
        Packclese.getApp().setToken(loginResponse.access_token)

        if (data.filePath == null) {
            Navigation.findNavController(view)
                    .navigate(R.id.action_signup_success, null)
            (activity as AuthActivity).toolbarSignUpSuccess()
        } else {
            Log.v("tamvan&berani", Packclese.getApp().getToken())
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(uploadPhotoResponse: UploadPhotoResponse, view: View) {
        val gson = Gson()
        val json = gson.toJson(uploadPhotoResponse.user)
        Packclese.getApp().setUser(json)
        
        Navigation.findNavController(view)
                .navigate(R.id.action_signup_success, null)
        (activity as AuthActivity).toolbarSignUpSuccess()
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    private fun initDummy(){
        etPhoneNumber.setText("085758145632")
        etAddress.setText("Jalan Jendelan Gajah")
        etHouseNumber.setText("155")
        etCity.setText("Depok")
    }
}