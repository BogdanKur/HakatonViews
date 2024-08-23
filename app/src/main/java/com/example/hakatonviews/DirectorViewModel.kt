package com.example.hakatonviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hakatonviews.Models.User
import com.example.hakatonviews.WorkerAuthFragment.Companion.users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class DirectorViewModel: ViewModel() {



    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        WorkerAuthFragment.users.addValueEventListener(object : ValueEventListener {
            override public fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<com.example.hakatonviews.Models.User>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue<com.example.hakatonviews.Models.User>() // Преобразуем данные в объект User
                    user?.let { userList.add(it) }
                }

                _users.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error getting users: ${error.message}")
            }

        })
    }

}