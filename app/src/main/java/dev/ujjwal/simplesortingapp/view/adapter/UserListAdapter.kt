package dev.ujjwal.simplesortingapp.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.ujjwal.simplesortingapp.R
import dev.ujjwal.simplesortingapp.model.UserDetail
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class UserListAdapter(private val context: Context, private var users: ArrayList<UserDetail>) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    fun updateUser(user: List<UserDetail>) {
        this.users.clear()
        this.users.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserListViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(users[position], context, position)
    }

    override fun getItemCount() = users.size

    class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPic = view.iv_pic
        private val tvName = view.tv_name
        private val tvGenderAge = view.tv_gender_age
        private val tvStatus = view.tv_status

        fun bind(user: UserDetail, context: Context, position: Int) {
            val options = RequestOptions()
                .placeholder(R.drawable.ic_baseline_person)
                .error(R.drawable.ic_baseline_person)
            Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(user.img)
                .into(ivPic)

            tvName.text = user.name

            when (user.gender) {
                "m" -> {
                    tvGenderAge.text = "MALE - ${user.age} YRS"
                }
                "f" -> {
                    tvGenderAge.text = "FEMALE - ${user.age} YRS"
                }
                else -> {
                    tvGenderAge.text = "${user.gender} - ${user.age} YRS"
                }
            }

            tvStatus.text = "${user.status?.toUpperCase()}\n${user.date}"
            when (user.status) {
                "active" -> {
                    tvStatus.setTextColor(Color.GREEN)
                }
                "left" -> {
                    tvStatus.setTextColor(Color.RED)
                }
                "onboarded" -> {
                    tvStatus.setTextColor(Color.MAGENTA)
                }
                else -> {
                    tvStatus.setTextColor(Color.LTGRAY)
                }
            }
        }
    }
}