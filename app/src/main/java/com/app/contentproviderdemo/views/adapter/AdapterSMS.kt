package com.app.contentproviderdemo.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoContacts
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoSMS
import com.app.contentproviderdemo.databinding.ItemLayoutContactsBinding
import com.app.contentproviderdemo.databinding.ItemLayoutSmsBinding

class AdapterSMS : RecyclerView.Adapter<AdapterSMS.ViewHolder>() {

    var dataList = ArrayList<PojoSMS>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding =
            ItemLayoutSmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setList(it: ArrayList<PojoSMS>?) {
        dataList.addAll(it!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var mBinding: ItemLayoutSmsBinding) :

        RecyclerView.ViewHolder(mBinding.root) {

        fun bind() {
            mBinding.model = dataList[adapterPosition]

        }

    }
}