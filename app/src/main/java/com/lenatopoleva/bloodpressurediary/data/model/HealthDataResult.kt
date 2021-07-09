package com.lenatopoleva.bloodpressurediary.data.model

sealed class HealthDataResult{
    data class Success<out T>(val data: T) : HealthDataResult()
    data class Error(val error: Throwable) : HealthDataResult()
    data class Loading(val progress: Int?) : HealthDataResult()
}
