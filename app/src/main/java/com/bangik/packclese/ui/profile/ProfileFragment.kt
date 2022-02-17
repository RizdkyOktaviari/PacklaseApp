package com.bangik.packclese.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangik.packclese.Packclese
import com.bangik.packclese.R
import com.bangik.packclese.model.response.login.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initview()

        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )

        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun initview (){

        var user = Packclese.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)

        if (userResponse.name.isNullOrEmpty()) {
            Toast.makeText(context, "data telah diubah harap di update", Toast.LENGTH_LONG).show()

        }else {
            nama.text = userResponse.name
            email.text = userResponse.email
        }
        if (!userResponse.profile_photo_path.isNullOrEmpty()) {

            Glide.with(requireActivity())
                .load(userResponse.profile_photo_path)
                .apply(RequestOptions.circleCropTransform())
                .into(gambar_user)

        }
    }


}