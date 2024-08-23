package com.example.hakatonviews


import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hakatonviews.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class WorkerViewModel: ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    // Ссылка на узел с пользователями в Firebase
    private val usersRef = WorkerAuthFragment.users

    // Карта для хранения времени для каждого пользователя
    private val _userTimes = MutableStateFlow<Map<String, Long>>(mutableMapOf())
    val userTimes: MutableStateFlow<Map<String, Long>> = _userTimes

    private var activeChronometer: Long? = null // Сохраняем время, когда был запущен хронометр
    private var userId: String? = null // ID пользователя, для которого идет хронометраж

    private val _offSet = MutableLiveData<Long?>(0)
    val offSet : MutableLiveData<Long?>
        get() = _offSet

    private val _chronometerBase = MutableLiveData<Long?>(0)
    val chronometerBase : MutableLiveData<Long?>
        get() = _chronometerBase


    init {
        // Наблюдение за изменениями в списке пользователей
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue<User>()
                    user?.let { userList.add(it) }
                }
                _users.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error getting users: ${error.message}")
            }
        })

        // Загрузка времени пользователей из Firebase
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userTimesMap = mutableMapOf<String, Long>()
                for (userSnapshot in snapshot.children) {
                    val userTime = userSnapshot.child("time").getValue<Long>()
                    _chronometerBase.value = userTime
                    if(userSnapshot.child("userId").getValue<Int>() != null) {
                        userId = userSnapshot.child("userId").getValue<Int>().toString()
                        Log.e("FuckE", userId!!)
                    }
                    if (userId != null && userTime != null) {
                        userTimesMap[userId!!.toString()] = userTime
                    }
                    val userTimeCount = userSnapshot.child("timeCount").getValue<Long>()
                    _offSet.value = userTimeCount
                }
                _userTimes.value = userTimesMap
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error getting user times: ${error.message}")
            }
        })
    }

    fun saveOffset() {
        _offSet.value = SystemClock.elapsedRealtime() - _chronometerBase.value!!
    }

    fun setBasetime() {
        _chronometerBase.value = SystemClock.elapsedRealtime() - _offSet.value!!
    }

    fun updateChronometer() {
        viewModelScope.launch {
            userTimes.collectLatest { userTimesMap ->
                if (userId != null) {
                    usersRef.child(userId!!).child("time").setValue(_chronometerBase.value)
                    Log.e("c", _chronometerBase.value.toString())
                    usersRef.child(userId!!).child("timeCount").setValue(_offSet.value)
                    Log.e("c", _offSet.value.toString())
                } else {
                    Log.e("usersWVM", "userId is null")
                }
            }
        }
    }
}