package com.dalima.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dalima.instagramclone.R
import com.dalima.instagramclone.databinding.PostRvBinding
import com.dalima.instagramclone.models.Post
import com.dalima.instagramclone.models.User
import com.dalima.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class PostAdapter(var context: Context, var postList: ArrayList<Post>):RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder(var binding: PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       var binding=PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
       return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user=it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.imgProfile)
                holder.binding.textname.text=user.name
            }
        }catch (e:Exception){

        }

        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.imgPost)
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.texttime.text=text
        }catch (e:java.lang.Exception){
            holder.binding.texttime.text=""
        }
        holder.binding.imgsend.setOnClickListener{
            var i = Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(i)
        }


        holder.binding.caption.text=postList.get(position).caption
        holder.binding.imglike.setOnClickListener{
            holder.binding.imglike.setImageResource(R.drawable.redlike)
        }


    }
}