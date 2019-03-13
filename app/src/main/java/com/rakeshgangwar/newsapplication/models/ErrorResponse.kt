package com.rakeshgangwar.newsapplication.models

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)