package com.example.hakatonviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hakatonviews.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class WorkerRegViewModel: ViewModel() {
    val emailRegEditText = MutableLiveData<String>("")
    val passwordRegEditText = MutableLiveData<String>("")

    // Функция для добавления нового пользователя
    fun addNewUser(email: String, password: String, time: Long) {
        // Получите текущий максимальный userId из базы данных
        WorkerAuthFragment.users.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var maxUserId = 0
                for (childSnapshot in snapshot.children) {
                    val userId = childSnapshot.child("userId").getValue<Int?>()
                    if (userId != null && userId > maxUserId) {
                        maxUserId = userId
                    }
                }

                val newUser = User(email, password, 0, 0, maxUserId + 1)
                WorkerAuthFragment.users.child((maxUserId + 1).toString()).setValue(newUser)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Firebase", "User added successfully")
                        } else {
                            Log.w("Firebase", "Error adding user", task.exception)
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Error getting users", error.toException())
            }
        })
    }
}