package com.example.hakatonviews

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hakatonviews.Models.User
import java.util.concurrent.TimeUnit

class UsersItemAdapter: RecyclerView.Adapter<UsersItemAdapter.UserItemViewHolder>() {


    var user = listOf<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = user.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder = UserItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val item = user[position]
        holder.bind(item)
    }

    class UserItemViewHolder(val rootView: CardView) : RecyclerView.ViewHolder(rootView) {

        val userName = rootView.findViewById<TextView>(R.id.user_name)
        val userTime = rootView.findViewById<TextView>(R.id.user_time)

        companion object {
            fun inflateFrom(parent: ViewGroup) : UserItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.users_item, parent, false) as CardView
                return UserItemViewHolder(view)
            }
        }

        fun bind(item: User) {
            userName.text = item.email
            val _chrom =  SystemClock.elapsedRealtime() - item.time!!
            userTime.text = formatTime(_chrom)
        }

        private fun formatTime(timeInMillis: Long): String {
            val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis)
            val hours = TimeUnit.SECONDS.toHours(totalSeconds)
            val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60
            val seconds = TimeUnit.SECONDS.toSeconds(totalSeconds) % 60

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}