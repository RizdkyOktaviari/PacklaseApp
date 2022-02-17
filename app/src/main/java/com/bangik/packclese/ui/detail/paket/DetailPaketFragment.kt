package com.bangik.packclese.ui.detail.paket

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
import com.bangik.packclese.model.response.rajaongkir.CekongkirResponse
import com.bangik.packclese.model.response.rajaongkir.KotaResponse
import com.bangik.packclese.model.response.rajaongkir.KotaTujuanResponse
import com.bangik.packclese.model.response.rajaongkir.ProvinsiResponse
import com.bangik.packclese.model.response.service.ServiceResponse
import com.bangik.packclese.ui.home.HomeContract
import com.bangik.packclese.ui.home.HomePresenter
import com.bangik.packclese.utils.Helpers
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail_bersih.*
import kotlinx.android.synthetic.main.fragment_detail_paket.*
import kotlinx.android.synthetic.main.fragment_detail_paket.btnOrderNow
import kotlinx.android.synthetic.main.fragment_detail_paket.ivPoster
import kotlinx.android.synthetic.main.fragment_detail_paket.ratingBar
import kotlinx.android.synthetic.main.fragment_detail_paket.tvDesc
import kotlinx.android.synthetic.main.fragment_detail_paket.tvTitle
import kotlinx.android.synthetic.main.fragment_detail_paket.tvTotal

class DetailPaketFragment : Fragment(), HomeContract.View, ProvinsiContract.View, KotaContract.View, KotaTujuanContract.View, PaketContract.View, CekongkirContract.View {

    private lateinit var presenterHome: HomePresenter
    private lateinit var presenterPaket: PaketPresenter
    private lateinit var presenterProvinsi: ProvinsiPresenter
    private lateinit var presenterKota: KotaPresenter
    private lateinit var presenterKotaTujuan: KotaTujuanPresenter
    private lateinit var presenterCekOngkir: CekongkirPresenter
    var progressDialog : Dialog? = null
    private var idProvAsal: String = "1"
    private var idProvTujuan: String = "1"
    private var idKotaAsal: String = "17"
    private var idKotaTujuan: String = "17"
    private var courier: String = "JNE"
    private lateinit var origin: String
    private lateinit var destination: String
    private lateinit var service: String
    private lateinit var etd: String
    private  var  ongkosKirim: Int = 0
    private  var  price: Int = 0
    private  var  total: Int = 0
    private lateinit var idPaket: String
    private lateinit var picturePaket: String
    private lateinit var jenisPaket: String
    var bundle:Bundle ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_paket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenterHome = HomePresenter(this)
        presenterHome.getHome()
        presenterPaket = PaketPresenter(this)
        presenterPaket.getPaket()
        presenterProvinsi = ProvinsiPresenter(this)
        presenterKota = KotaPresenter(this)
        presenterKotaTujuan = KotaTujuanPresenter(this)
        presenterProvinsi.getProvinsi()
        presenterCekOngkir = CekongkirPresenter(this)
        initView()

        btnOrderNow.setOnClickListener {
            if (ongkosKirim == 0){
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }else if(etBerat.text.isNullOrEmpty()){
                etBerat.error = "Berat tidak boleh kosong"
                etBerat.requestFocus()
            }else if(etAlamat.text.isNullOrEmpty()){
                etAlamat.error = "Alamat tidak boleh kosong"
                etAlamat.requestFocus()
            }else{
                total = price + ongkosKirim
                val dataPaket = DataPaket(
                    total.toString(),
                    idPaket,
                    etAlamat.text.toString(),
                    origin,
                    destination,
                    etBerat.text.toString(),
                    courier + " " + service,
                    ongkosKirim.toString(),
                    jenisPaket,
                    picturePaket,
                    price.toString(),
                    etd
                )
                bundle = bundleOf("dataPaket" to dataPaket)
                Navigation.findNavController(it).navigate(R.id.action_detailPaketFragment_to_paymentPaketFragment, bundle)
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

        etBerat.setText("500")

        val kurir = arrayOf("JNE", "TIKI", "POS")
        spinerKurir.adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, kurir) }
        spinerKurir.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                courier = spinerKurir.selectedItem.toString()
                if (!etBerat.text.isNullOrEmpty()){
                    presenterCekOngkir.cekOngkir(idKotaAsal, idKotaTujuan, etBerat.text.toString(), courier)
                }else{
                    Toast.makeText(context, "Berat tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun onProvinsiSuccess(provinsiResponse: ProvinsiResponse) {
        var name = arrayOf<String>()
        for(i in provinsiResponse.data.indices){
            name = Helpers.append(name, provinsiResponse.data[i].province)
        }

        spinerProvAsal.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }
        spinerProvAsal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in provinsiResponse.data.indices){
                    if (provinsiResponse.data[i].province.equals(spinerProvAsal.selectedItem.toString())){
                        idProvAsal = provinsiResponse.data[i].province_id.toString()
                    }
                }
                presenterKota.getKota(idProvAsal)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinerProvTujuan.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }
        spinerProvTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in provinsiResponse.data.indices){
                    if (provinsiResponse.data[i].province.equals(spinerProvTujuan.selectedItem.toString())){
                        idProvTujuan = provinsiResponse.data[i].province_id.toString()
                    }
                }
                presenterKotaTujuan.getKotaTujuan(idProvTujuan)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onProvinsiFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onKotaSuccess(kotaResponse: KotaResponse) {
        var name = arrayOf<String>()
        for(i in kotaResponse.data.indices){
            name = Helpers.append(name, kotaResponse.data[i].type + " " + kotaResponse.data[i].city_name)
        }

        spinerKotaAsal.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }
        spinerKotaAsal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in kotaResponse.data.indices){
                    val kota = kotaResponse.data[i].type + " " + kotaResponse.data[i].city_name
                    if (kota.equals(spinerKotaAsal.selectedItem.toString())){
                        idKotaAsal = kotaResponse.data[i].city_id
                        origin = kotaResponse.data[i].type + " " + kotaResponse.data[i].city_name
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onKotaFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onKotaTujuanSuccess(kotaTujuanResponse: KotaTujuanResponse) {
        var name = arrayOf<String>()
        for(i in kotaTujuanResponse.data.indices){
            name = Helpers.append(name, kotaTujuanResponse.data[i].type + " " + kotaTujuanResponse.data[i].city_name)
        }

        spinerKotaTujuan.adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }
        spinerKotaTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (i in kotaTujuanResponse.data.indices){
                    val kota = kotaTujuanResponse.data[i].type + " " + kotaTujuanResponse.data[i].city_name
                    if (kota.equals(spinerKotaTujuan.selectedItem.toString())){
                        idKotaTujuan = kotaTujuanResponse.data[i].city_id
                        destination = kotaTujuanResponse.data[i].type + " " + kotaTujuanResponse.data[i].city_name
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onKotaTujuanFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPaketSuccess(paketResponse: ServiceResponse) {
        price = paketResponse.data[0].price
        idPaket = paketResponse.data[0].id.toString()
        tvTotal.formatPrice(paketResponse.data[0].price.toString())
    }

    override fun onPaketFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        Glide.with(requireContext())
            .load(homeResponse.data[2].picturePath)
            .into(ivPoster)
        tvTitle.text = homeResponse.data[2].jenis.toString()
        tvDesc.text = homeResponse.data[2].description.toString()
        ratingBar.rating = homeResponse.data[2].rate
        jenisPaket = homeResponse.data[2].jenis.toString()
        picturePaket = homeResponse.data[2].picturePath.toString()
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCekongkirSuccess(cekongkirResponse: CekongkirResponse) {
        var name = arrayOf<String>()
        for(i in cekongkirResponse.data[0].costs.indices){
            var cekOngkir = cekongkirResponse.data[0].costs[i].service + " Estimasi " + cekongkirResponse.data[0].costs[i].cost[0].etd + " Hari Rp. " + cekongkirResponse.data[0].costs[i].cost[0].value
            name = Helpers.append(name, cekOngkir)
        }

        spinerLayananKurir.adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, name) }
        spinerLayananKurir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                for(i in cekongkirResponse.data[0].costs.indices){
                    var selectedItem = cekongkirResponse.data[0].costs[i].service + " Estimasi " + cekongkirResponse.data[0].costs[i].cost[0].etd + " Hari Rp. " + cekongkirResponse.data[0].costs[i].cost[0].value
                    if (selectedItem.equals(spinerLayananKurir.selectedItem.toString())){
                        ongkosKirim = cekongkirResponse.data[0].costs[i].cost[0].value
                        service = cekongkirResponse.data[0].costs[i].service
                        etd = cekongkirResponse.data[0].costs[i].cost[0].etd
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onCekongkirFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}