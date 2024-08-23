package com.example.hakatonviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class WorkerAutnViewModel: ViewModel() {
    val emailAuthEditText = MutableLiveData<String>("")
    val passwordAuthEditText = MutableLiveData<String>("")




}