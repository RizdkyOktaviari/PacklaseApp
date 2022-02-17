package com.bangik.packclese.ui.detail.bersih

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangik.packclese.R
import kotlinx.android.synthetic.main.fragment_payment_bersih_success.*

class PaymentBersihSuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_bersih_success, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DetailBersihActivity).toolbarDetail()

        btnOtherFood.setOnClickListener {
            requireActivity().finish()
        }

        btnMyOrder.setOnClickListener {
            requireActivity().finish()
        }
    }
}