package com.example.test1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_bigcard.view.*

class mAdapter() :RecyclerView.Adapter<mAdapter.mViewHolder>(){

    inner class mViewHolder(view: View):RecyclerView.ViewHolder(view){
        var imgv = view.imgv_bigcard
        var title = view.tv_title_bigcard
        var content = view.tv_content_bigcard
        fun bind(){
            imgv.setImageResource(R.drawable.bn_12)
            title.text = "標題文字標題文字標題文字標題文字標題文字"
            content.text = "內容文字內容文字內容文字內容文字內容文字"
        }

    }

    override fun getItemCount() = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_bigcard,parent,false)
        return mViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind()
    }
}