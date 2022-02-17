package com.bangik.packclese.ui.detail.titip

import android.app.DatePickerDialog
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
import com.bangik.packclese.utils.Helpers
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_bersih.*
import kotlinx.android.synthetic.main.fragment_detail_titip.*
import kotlinx.android.synthetic.main.fragment_detail_titip.btnOrderNow
import kotlinx.android.synthetic.main.fragment_detail_titip.ivPoster
import kotlinx.android.synthetic.main.fragment_detail_titip.ratingBar
import kotlinx.android.synthetic.main.fragment_detail_titip.tvDesc
import kotlinx.android.synthetic.main.fragment_detail_titip.tvTitle
import kotlinx.android.synthetic.main.fragment_detail_titip.tvTotal
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class DetailTitipFragment : Fragment(), TitipContract.View, HomeContract.View {
    private lateinit var presenterTitip: TitipPresenter
    private lateinit var presenterHome: HomePresenter
    var progressDialog : Dialog? = null
    var day = 0
    var month = 0
    var year = 0
    var tambahanBox = 0
    private lateinit var date1: Date
    private lateinit var date2: Date
    val dates = SimpleDateFormat("dd - MM - yyyy")
    val datesFormat = SimpleDateFormat("dd-MM-yyyy")
    private  var  price: Int = 0
    private  var  total: Int = 0
    private lateinit var jenisTitip: String
    private lateinit var pictureTitip: String
    private lateinit var idTitip: String
    var bundle:Bundle ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_titip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailTitipActivity).toolbarDetail()

        initView()
        presenterHome = HomePresenter(this)
        presenterHome.getHome()

        presenterTitip = TitipPresenter(this)
        presenterTitip.getTitip()

        btnOrderNow.setOnClickListener {
            if(etDateStart.text.isNullOrEmpty()){
                etDateStart.error = "Tanggal tidak boleh kosong"
                etDateStart.requestFocus()
            }else if(etDateEnd.text.isNullOrEmpty()){
                etDateEnd.error = "Tanggal tidak boleh kosong"
                etDateEnd.requestFocus()
            }else if(etJumlah.text.isNullOrEmpty()){
                etJumlah.error = "Jumlah tidak boleh kosong"
                etJumlah.requestFocus()
            }else{
                date1 = dates.parse(etDateStart.text.toString())
                date2 = dates.parse(etDateEnd.text.toString())
                val difference: Long = abs(date1.time - date2.time)
                var differenceDates = difference / (86400 * 1000)
                if (jenisTitip.contains("arian", ignoreCase = true)){
                    total = (price * etJumlah.text.toString().toInt() * differenceDates.absoluteValue).toInt()
                }else if(jenisTitip.contains("ulanan", ignoreCase = true)){
                    if (differenceDates.absoluteValue.toInt() < 30){
                        differenceDates = 1
                    }else{
                        differenceDates = differenceDates / 30
                    }
                    total = (price * etJumlah.text.toString().toInt() * differenceDates.absoluteValue).toInt()
                }else{
                    if (etJumlah.text.toString().toInt() > 3){
                        tambahanBox = (etJumlah.text.toString().toInt() - 3) * 20000
                    }

                    if (differenceDates.absoluteValue.toInt() < 30){
                        differenceDates = 1
                    }else{
                        differenceDates = differenceDates / 30
                    }
                    total = ((price + tambahanBox) * differenceDates.absoluteValue).toInt()
                }

                val dataTitip = DataTitip(
                    datesFormat.format(date1).toString(),
                    datesFormat.format(date2).toString(),
                    differenceDates.absoluteValue.toString(),
                    tambahanBox,
                    etJumlah.text.toString(),
                    price,
                    total,
                    jenisTitip,
                    idTitip,
                    pictureTitip
                )

                bundle = bundleOf("dataTitip" to dataTitip)
                Navigation.findNavController(it).navigate(R.id.action_detailTitipFragment_to_paymentTitipFragment, bundle)
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

        etDateStart.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)


            val datePickerDialog =
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    etDateStart.setText("""$dayOfMonth - ${month + 1} - $year""")
                }, year, month,day)

            datePickerDialog.show()
        }

        etDateEnd.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            val datePickerDialog =
                DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    etDateEnd.setText("""$dayOfMonth - ${month + 1} - $year""")
                }, year, month,day)

            datePickerDialog.show()
        }
    }

    override fun onTitipSuccess(titipResponse: ServiceResponse) {
        var name = arrayOf<String>()
        for(i in titipResponse.data.indices){
            name = Helpers.append(name, titipResponse.data[i].name)
        }
        spinerTitip.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }

        spinerTitip.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in titipResponse.data.indices){
                    if (titipResponse.data[i].name.equals(spinerTitip.selectedItem.toString())){
                        price = titipResponse.data[i].price
                        jenisTitip = titipResponse.data[i].name
                        idTitip = titipResponse.data[i].id.toString()
                    }
                }

                tvTotal.formatPrice(price.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onTitipFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Glide.with(requireContext())
            .load(homeResponse.data[3].picturePath)
            .into(ivPoster)
        tvTitle.text = homeResponse.data[3].jenis
        tvDesc.text = homeResponse.data[3].description
        ratingBar.rating = homeResponse.data[3].rate
        pictureTitip = homeResponse.data[3].picturePath.toString()
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