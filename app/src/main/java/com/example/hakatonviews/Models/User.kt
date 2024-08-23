package com.example.hakatonviews.Models

data class User(
    val email: String? = null,
    val password: String? = null,
    val time: Long? = null,
    val timeCount: Long? = null,
    val userId: Int? = null
) {
    constructor() : this(null, null, null, null, null) // Конструктор без аргументов
}


