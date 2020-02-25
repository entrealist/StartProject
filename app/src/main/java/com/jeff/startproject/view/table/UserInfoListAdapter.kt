package com.jeff.startproject.view.table

import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.jeff.startproject.R
import com.jeff.startproject.model.api.user.UserItem
import com.jeff.startproject.view.table.viewholder.UserInfoViewHolder

class UserInfoListAdapter : PagedListAdapter<UserItem, UserInfoViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.item_user_info,
            parent,
            false
        )
        return UserInfoViewHolder(view)
    }

    private val shapeArray = SparseArray<ShapeAppearanceModel>().also {
        it.put(
            0, ShapeAppearanceModel.builder()
                .build()
        )

        it.put(
            1, ShapeAppearanceModel.builder()
                .setAllCornerSizes(ShapeAppearanceModel.PILL)
                .build()
        )

        it.put(
            2, ShapeAppearanceModel.builder()
                .setAllCorners(CornerFamily.CUT, 0f)
                .setAllCornerSizes(ShapeAppearanceModel.PILL)
                .build()
        )
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        getItem(position).let {

            // 方法1: 處理ImageView外框, 呈現圓形.
            holder.binding.ivAvatar.shapeAppearanceModel = shapeArray[position % 3]

            // user avatar
            Glide.with(holder.itemView.context)
                .load(it?.avatarUrl)
                // 方法2: 使用Glide將圖片轉為圓形
                //.apply(RequestOptions.circleCropTransform())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.binding.ivAvatar)

            // user login
            it?.login.run {
                holder.binding.tvName.text = it?.login
            }

            // user badge
            if (it!!.isSiteAdmin!!) {
                holder.binding.tvBadge.visibility = View.VISIBLE
            } else {
                holder.binding.tvBadge.visibility = View.GONE
            }
        }
    }
}
