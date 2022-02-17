package com.bangik.packclese.ui.detail.bersih

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangik.packclese.R
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailBersihActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bersih)
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