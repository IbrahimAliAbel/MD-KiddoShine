package com.example.kiddoshine.api.response

data class UserRequest(
    val name: String,
    val email: String,
    val password: String
)
data class UserModel(
    val email: String,
    val token: String,
    val isLoggedIn: Boolean
)
