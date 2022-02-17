package com.bangik.packclese.network

import com.bangik.packclese.model.response.Wrapper
import com.bangik.packclese.model.response.checkout.CheckoutBersihResponse
import com.bangik.packclese.model.response.checkout.CheckoutLaundryResponse
import com.bangik.packclese.model.response.checkout.CheckoutPaketResponse
import com.bangik.packclese.model.response.checkout.CheckoutTitipResponse
import com.bangik.packclese.model.response.home.HomeResponse
import com.bangik.packclese.model.response.login.LoginResponse
import com.bangik.packclese.model.response.login.UploadPhotoResponse
import com.bangik.packclese.model.response.profile.ProfileEditResponse
import com.bangik.packclese.model.response.rajaongkir.CekongkirResponse
import com.bangik.packclese.model.response.rajaongkir.KotaResponse
import com.bangik.packclese.model.response.rajaongkir.KotaTujuanResponse
import com.bangik.packclese.model.response.rajaongkir.ProvinsiResponse
import com.bangik.packclese.model.response.service.ServiceResponse
import com.bangik.packclese.model.response.transaction.TransactionResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface Endpoint {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email:String,
              @Field("password") password:String) : Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name:String,
                 @Field("email") email:String,
                 @Field("password") password:String,
                 @Field("password_confirmation") password_confirmation:String,
                 @Field("address") address:String,
                 @Field("phoneNumber") phoneNumber:String) : Observable<Wrapper<LoginResponse>>

    @GET("jenis-layanan")
    fun home() : Observable<Wrapper<HomeResponse>>

    @GET("data-services?id=1")
    fun laundry() : Observable<Wrapper<ServiceResponse>>

    @FormUrlEncoded
    @POST("checkout-laundry")
    fun checkoutLaundry(@Field("user_id") user_id:String,
                        @Field("total") total:String,
                        @Field("service_id") service_id:String,
                        @Field("address") address:String,
                        @Field("weight") weight:String,
                        @Field("antar") antar:String,
                        @Field("paymentMethod") paymentMethod:String) : Observable<Wrapper<CheckoutLaundryResponse>>

    @GET("data-services?id=2")
    fun bersih() : Observable<Wrapper<ServiceResponse>>

    @FormUrlEncoded
    @POST("checkout-bersih")
    fun checkoutBersih(@Field("user_id") user_id:String,
                       @Field("total") total:String,
                       @Field("service_id") service_id:String,
                       @Field("address") address:String,
                       @Field("space") space:String,
                       @Field("paymentMethod") paymentMethod:String) : Observable<Wrapper<CheckoutBersihResponse>>

    @Multipart
    @POST("user/photo")
    fun registerPhoto(@Part profileImage: MultipartBody.Part) : Observable<Wrapper<UploadPhotoResponse>>

    @FormUrlEncoded
    @POST("user/edit")
    fun edit(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("phoneNumber") phoneNumber: String) : Observable<Wrapper<ProfileEditResponse>>

    @GET("history-transactions")
    fun transaction() : Observable<Wrapper<TransactionResponse>>

    @GET("data-provinsi")
    fun dataProvinsi() : Observable<Wrapper<ProvinsiResponse>>

    @GET("data-kota/{id}")
    fun datakota(
        @Path(value = "id") id:String
    ) : Observable<Wrapper<KotaResponse>>

    @GET("data-kota/{id}")
    fun datakotaTujuan(
        @Path(value = "id") id:String
    ) : Observable<Wrapper<KotaTujuanResponse>>

    @GET("data-services?id=3")
    fun paket() : Observable<Wrapper<ServiceResponse>>

    @GET("hitung-ongkir")
    fun cekOngkir(
        @Query("origin") origin:String,
        @Query("destination") destination:String,
        @Query("weight") weight:String,
        @Query("courier") courier:String
    ) : Observable<Wrapper<CekongkirResponse>>

    @FormUrlEncoded
    @POST("checkout-paket")
    fun checkoutPaket(@Field("user_id") user_id:String,
                      @Field("total") total:String,
                      @Field("service_id") service_id:String,
                      @Field("address") address:String,
                      @Field("origin") origin:String,
                      @Field("destination") destination:String,
                      @Field("weight") weight:String,
                      @Field("courier") courier:String,
                      @Field("ongkir") ongkir:String,
                      @Field("paymentMethod") paymentMethod:String) : Observable<Wrapper<CheckoutPaketResponse>>

    @GET("data-services?id=4")
    fun titip() : Observable<Wrapper<ServiceResponse>>

    @FormUrlEncoded
    @POST("checkout-titip")
    fun checkoutTitip(@Field("user_id") user_id:String,
                      @Field("total") total:String,
                      @Field("service_id") service_id:String,
                      @Field("address") address:String,
                      @Field("start") start:String,
                      @Field("end") end:String,
                      @Field("quantity") quantity:String,
                      @Field("paymentMethod") paymentMethod:String) : Observable<Wrapper<CheckoutTitipResponse>>

    @POST("logout")
    fun logout() : Observable<Wrapper<Any>>
}