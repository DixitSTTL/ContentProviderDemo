package com.app.contentproviderdemo.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoContacts
import com.app.contentproviderdemo.databinding.ItemLayoutContactsBinding

class AdapterContacts : RecyclerView.Adapter<AdapterContacts.ViewHolder>() {

    var dataList = ArrayList<PojoContacts>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding =
            ItemLayoutContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setList(it: ArrayList<PojoContacts>?) {
        dataList.addAll(it!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var mBinding: ItemLayoutContactsBinding) :

        RecyclerView.ViewHolder(mBinding.root) {

        fun bind() {
            mBinding.model = dataList[adapterPosition]

        }

    }
}