package com.bangik.packclese.ui.order.pastorders

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangik.packclese.R
import com.bangik.packclese.model.response.transaction.Data
import kotlinx.android.synthetic.main.fragment_profile_account.*

class PastordersFragment : Fragment(), PastordersAdapter.ItemAdapterCallback {

    private var adapter: PastordersAdapter?=null
    var pastordersList:ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pastorders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pastordersList = arguments?.getParcelableArrayList("data")

        if (!pastordersList.isNullOrEmpty()) {
            adapter= PastordersAdapter(pastordersList!!, this)
            val layoutManager = LinearLayoutManager(activity)
            rcList.layoutManager = layoutManager
            rcList.adapter = adapter
        }
    }

    override fun onClick(v: View, data: Data) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("http://47.254.246.113:82/profile/detail-riwayat-transaksi/"+data.id)
        startActivity(i)
    }

}