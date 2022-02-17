package com.bangik.packclese.ui.order

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bangik.packclese.R
import com.bangik.packclese.model.response.transaction.Data
import com.bangik.packclese.model.response.transaction.TransactionResponse
import com.readystatesoftware.chuck.internal.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class OrderFragment : Fragment(), OrderContract.View {

    lateinit var presenter: OrderPresenter
    var progressDialog: Dialog?=null

    var inprogressList:ArrayList<Data>? = ArrayList()
    var pastordersList:ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        presenter = OrderPresenter(this)
        presenter.getTransaction()

        findServices.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.navigation_home)
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

        include_toolbar.toolbar.title = "Your Orders"
        include_toolbar.toolbar.subtitle = "Wait for the best service"
    }

    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        if (transactionResponse.data.isNullOrEmpty()) {
            ll_empty.visibility = View.VISIBLE
            ll_tab.visibility = View.GONE
            include_toolbar.visibility = View.GONE
        } else {

            for (a in transactionResponse.data.indices) {
                if (transactionResponse.data[a].status.equals("PROCESS", true)
                    || transactionResponse.data[a].status.equals("PENDING", true)) {
                    inprogressList?.add(transactionResponse.data[a])
                } else if (transactionResponse.data[a].status.equals("CANCELLED", true)
                    || transactionResponse.data[a].status.equals("SUCCESS", true)) {
                    pastordersList?.add(transactionResponse.data[a])
                }
            }

            val sectionsPagerAdapter = SectionPagerAdapter(
                childFragmentManager
            )
            sectionsPagerAdapter.setData(inprogressList, pastordersList)
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)

        }
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}