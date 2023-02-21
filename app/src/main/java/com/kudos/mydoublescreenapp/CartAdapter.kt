package com.kudos.mydoublescreenapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kudos.mydoublescreenapp.databinding.RowItemCartBinding

class CartAdapter(private val onItemClick: (CartItem) -> Unit) :
    ListAdapter<CartItem, CartAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(
                oldItem: CartItem,
                newItem: CartItem
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CartItem,
                newItem: CartItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val rowItemCartBinding: RowItemCartBinding) :
        RecyclerView.ViewHolder(rowItemCartBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            rowItemCartBinding.apply {
                val item = getItem(position)
                itemNameTextView.text = item.itemName
                itemView.setOnClickListener {
                    Log.d("TAG", "onBindViewHolder: ${item.itemName}")
                    Toast.makeText(itemView.context,item.itemName, Toast.LENGTH_SHORT).show()
                    //onItemClick(item)
                }

            }
        }
    }

}