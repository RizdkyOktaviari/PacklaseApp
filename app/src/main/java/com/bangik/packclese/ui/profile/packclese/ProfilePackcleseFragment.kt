package com.bangik.packclese.ui.profile.packclese

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangik.packclese.R
import com.bangik.packclese.model.dummy.ProfileMenuModel
import com.bangik.packclese.ui.detail.bersih.DetailBersihActivity
import com.bangik.packclese.ui.profile.ProfileActivity
import com.bangik.packclese.ui.profile.ProfileMenuAdapter
import kotlinx.android.synthetic.main.fragment_profile_account.*

class ProfilePackcleseFragment : Fragment(), ProfileMenuAdapter.ItemAdapterCallback {

    private var menuArrayList: ArrayList<ProfileMenuModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_packclese, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDataDummy()

        var adapter = ProfileMenuAdapter(menuArrayList, this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter
    }

    fun initDataDummy(){
        menuArrayList = ArrayList()
        menuArrayList.add(ProfileMenuModel("Rate Apps"))
        menuArrayList.add(ProfileMenuModel("Help Center"))
        menuArrayList.add(ProfileMenuModel("Privacy & Policy"))
        menuArrayList.add(ProfileMenuModel("Term & Condition"))
    }

    override fun onClick(v: View, data: ProfileMenuModel) {
        if (data.title == "Privacy & Policy"){
            val detail = Intent(activity, ProfileActivity::class.java).putExtra("pages", 1)
            startActivity(detail)
        }else if(data.title == "Term & Condition"){
            val detail = Intent(activity, ProfileActivity::class.java).putExtra("pages", 2)
            startActivity(detail)
        }else if(data.title == "Help Center"){
            Toast.makeText(context, "Menuju Link Video Help Center", Toast.LENGTH_SHORT).show()
        }else if(data.title == "Rate Apps"){
            Toast.makeText(context, "Menuju Google Apps untuk rate app", Toast.LENGTH_SHORT).show()
        }
    }
}