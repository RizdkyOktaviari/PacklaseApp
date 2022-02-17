package com.bangik.packclese.ui.detail.laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangik.packclese.R
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailLaundryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_laundry)
    }

    fun toolbarDetail() {
        toolbar.visibility = View.GONE
    }

    fun toolbarPayment() {
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Payment"
        toolbar.subtitle = "You choose good service"
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_000, null)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}