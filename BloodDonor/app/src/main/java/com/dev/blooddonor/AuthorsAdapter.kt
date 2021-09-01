package com.dev.blooddonor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.blooddonor.model.OnClickListeners
import com.dev.blooddonor.model.User
import kotlinx.android.synthetic.main.item_user.view.*


class AuthorsAdapter (val onClickListeners: OnClickListeners): RecyclerView.Adapter<AuthorsAdapter.AuthorViewModel>() {

    private var authors = mutableListOf<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AuthorViewModel(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user, parent, false)
    )

    override fun getItemCount() = authors.size

    override fun onBindViewHolder(holder: AuthorViewModel, position: Int) {
        val user=authors[position]
        holder.view.tvFirstName.text = user.name
        holder.view.tvAge.text=user.age
        holder.view.tvGroupType.text=user.bloodGroup
//        holder.view.tvLength.text=user.id

        if (user.profileImage == "") {
            holder.itemView.profile_image.setImageResource(R.drawable.profile_image)
        } else {
            holder.itemView.profile_image.let { Glide.with(it).load(user.profileImage).into(holder.itemView.profile_image) }
        }


        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context, UserDetailsActivity::class.java)
            intent.putExtra("name", user.name)
            intent.putExtra("age", user.age)
            intent.putExtra("bloodGroup", user.bloodGroup)
            intent.putExtra("phoneNumber", user.phoneNumber)
            intent.putExtra("email", user.eamil)
            intent.putExtra("imageUrl", user.profileImage)
            intent.putExtra("address", user.userAddress)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.ivNumber.setOnClickListener {
             user.phoneNumber?.let { it1 -> onClickListeners.onClicked(position, it1) }
        }

    }


    fun setAuthors(authors: List<User>) {
        this.authors = authors as MutableList<User>
        notifyDataSetChanged()
    }
    fun addAuthor(author: User) {
        if (!authors.contains(author)) {
            authors.add(author)
        } else {
            val index = authors.indexOf(author)
                authors[index] = author
            }
                notifyDataSetChanged()
    }
    class AuthorViewModel(val view: View) : RecyclerView.ViewHolder(view)
}