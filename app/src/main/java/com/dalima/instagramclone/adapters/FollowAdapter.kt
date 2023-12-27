package com.dalima.instagramclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalima.instagramclone.R
import com.dalima.instagramclone.databinding.FollowRvBinding
import com.dalima.instagramclone.models.User

class FollowAdapter(var context: android.content.Context, var folloList: ArrayList<User>):RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     var binding=FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return folloList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     Glide.with(context).load(folloList.get(position).image).placeholder(R.drawable.user).into(holder.binding.imgProfile)
        holder.binding.name.text=folloList.get(position).name
    }
}