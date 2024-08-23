package com.example.hakatonviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DirectorAuthViewModel: ViewModel() {
    val emailAuthEditText = MutableLiveData<String>("")
    val passwordAuthEditText = MutableLiveData<String>("")
}