package com.example.hakatonviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DirectorRegViewModel: ViewModel() {
    val emailRegEditText = MutableLiveData<String>("")
    val passwordRegEditText = MutableLiveData<String>("")
}