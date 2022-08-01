package com.tolganacar.kotlincryptotracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tolganacar.kotlincryptotracker.databinding.RecyclerRowBinding
import com.tolganacar.kotlincryptotracker.model.CryptoModel

class CryptoAdapter(val cryptoList : ArrayList<CryptoModel>) : RecyclerView.Adapter<CryptoAdapter.CryptoHolder>(){

    val colors : Array<String> = arrayOf("#8d7dff", "#5cff75", "#c078aa","#005a80","#ffa2a5","#ff7434")

    class CryptoHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.binding.textViewCurrency.text = cryptoList.get(position).currency
        holder.binding.textViewPrice.text = cryptoList.get(position).price
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}