package com.jeff.startproject.view.pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jeff.startproject.R

class MyPagerAdapter : ListAdapter<MyPagerItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MyPagerItem>() {
            override fun areItemsTheSame(oldItem: MyPagerItem, newItem: MyPagerItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: MyPagerItem, newItem: MyPagerItem): Boolean {
                return oldItem.color == newItem.color
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflateWithLayout(parent, R.layout.item_view_pager)
        return MyPagerViewHolder(view)
    }

    private fun inflateWithLayout(viewGroup: ViewGroup, @LayoutRes layoutRes: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(layoutRes, viewGroup, false)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyPagerViewHolder
        holder.bind(getItem(position))
    }
}