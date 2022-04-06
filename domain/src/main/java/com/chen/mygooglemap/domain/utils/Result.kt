package com.chen.mygooglemap.domain.utils

import java.io.Serializable

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
): Serializable {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
}