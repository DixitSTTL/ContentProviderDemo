package com.app.contentproviderdemo.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoImages
import com.app.contentproviderdemo.databinding.ItemLayoutImagesBinding

class AdapterImages : RecyclerView.Adapter<AdapterImages.ViewHolder>() {

    var dataList = ArrayList<PojoImages>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding =
            ItemLayoutImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setList(it: ArrayList<PojoImages>?) {
        dataList.addAll(it!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var mBinding: ItemLayoutImagesBinding) :

        RecyclerView.ViewHolder(mBinding.root) {

        fun bind() {
            mBinding.model = dataList[adapterPosition]

        }

    }
}