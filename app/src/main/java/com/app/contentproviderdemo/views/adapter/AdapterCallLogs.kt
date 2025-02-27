package com.app.contentproviderdemo.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoCallLogs
import com.app.contentproviderdemo.databinding.ItemLayoutCallLogsBinding

class AdapterCallLogs : RecyclerView.Adapter<AdapterCallLogs.ViewHolder>() {

    var dataList = ArrayList<PojoCallLogs>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding =
            ItemLayoutCallLogsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setList(it: ArrayList<PojoCallLogs>?) {
        dataList= it!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(var mBinding: ItemLayoutCallLogsBinding) :

        RecyclerView.ViewHolder(mBinding.root) {

        fun bind() {
            mBinding.model = dataList[adapterPosition]

        }

    }
}