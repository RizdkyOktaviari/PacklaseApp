package com.bangik.packclese.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangik.packclese.R
import com.bangik.packclese.model.response.transaction.Data
import com.bangik.packclese.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_pastorders.view.*

class PastordersAdapter (
    private val listData : List<Data>,
    private val itemAdapterCallback : ItemAdapterCallback
) : RecyclerView.Adapter<PastordersAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_pastorders, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data : Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tvTitle.text = data.detail_transaction[0].service.name
                tvPrice.formatPrice(data.detail_transaction[0].service.price.toString())

                Glide.with(context)
                    .load(data.detail_transaction[0].service.jenis_services.picturePath)
                    .into(ivPoster)

                if(data.status.equals("CANCELLED", true)) {
                    tvCancelled.visibility = View.VISIBLE
                }

                itemView.setOnClickListener { itemAdapterCallback.onClick(it, data) }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data:Data)
    }

}