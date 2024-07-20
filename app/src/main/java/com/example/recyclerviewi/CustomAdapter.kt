package com.example.recyclerviewi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewi.databinding.ItemviewBinding

class CustomAdapter(private val dataList : ArrayList<DataEntity>, private val interFace : ClickInterface) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(val binding : ItemviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.name.text = dataList[position].name
        holder.binding.roll.text = dataList[position].roll
        holder.binding.update.setOnClickListener {
            interFace.clickUpdate(position,holder)
        }
        holder.binding.delete.setOnClickListener {
            interFace.clickDelete(position)
        }
    }
}