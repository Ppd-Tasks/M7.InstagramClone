package com.example.instagramclonekt.adapter

import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclonekt.R
import com.example.instagramclonekt.fragment.HomeFragment
import com.example.instagramclonekt.manager.AuthManager
import com.example.instagramclonekt.model.Post
import com.google.android.material.imageview.ShapeableImageView

class HomeAdapter(var fragment:HomeFragment, var items:ArrayList<Post>):BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_home, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post:Post = items[items.size - position - 1]
       // val post:Post = items[position]

        if (holder is PostViewHolder){
            val iv_post = holder.iv_post
            val tv_fullname = holder.tv_fullname
            val tv_fullname1 = holder.tv_fullname1
            val iv_profile = holder.iv_profile
            val tv_caption = holder.tv_caption
            val tv_time = holder.tv_time
            val iv_like = holder.iv_like
            val iv_more = holder.iv_more

            tv_fullname.text = post.fullname
            tv_fullname1.text = post.fullname
            tv_caption.text = post.caption
            tv_time.text = post.currentDate

            Glide.with(fragment).load(post.userImg).placeholder(R.drawable.ic_person).error(R.drawable.ic_person).into(iv_profile)
            Glide.with(fragment).load(post.postImg).into(iv_post)

            iv_like.setOnClickListener {
                if(post.isLiked){
                    post.isLiked = false
                    iv_like.setImageResource(R.drawable.ic_like)
                }else{
                    post.isLiked = true
                    iv_like.setImageResource(R.drawable.ic_liked)
                }
                fragment.likeOrUnlikePost(post)
            }

            if(post.isLiked){
                iv_like.setImageResource(R.drawable.ic_liked)
            }else{
                iv_like.setImageResource(R.drawable.ic_like)
            }

            val uid = AuthManager.currentUser()!!.uid
            if (uid == post.uid){
                iv_more.visibility = View.VISIBLE
            }else{
                iv_more.visibility = View.GONE
            }
            iv_more.setOnClickListener {
                fragment.showDeleteDialog(post)
            }
        }
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_profile:ShapeableImageView
        val iv_post:ShapeableImageView
        val tv_fullname:TextView
        val tv_fullname1:TextView
        val tv_time:TextView
        val tv_caption:TextView
        val iv_more:ImageView
        val iv_like:ImageView
        val iv_share:ImageView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            iv_post = view.findViewById(R.id.iv_post)
            tv_fullname = view.findViewById(R.id.tv_fullname)
            tv_fullname1 = view.findViewById(R.id.tv_fullname1)
            tv_time = view.findViewById(R.id.tv_time)
            tv_caption = view.findViewById(R.id.tv_caption)
            iv_more = view.findViewById(R.id.iv_more)
            iv_like = view.findViewById(R.id.iv_like)
            iv_share = view.findViewById(R.id.iv_share)
        }
    }
}