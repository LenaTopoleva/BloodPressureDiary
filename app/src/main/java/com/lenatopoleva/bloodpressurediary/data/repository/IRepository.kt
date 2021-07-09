package com.lenatopoleva.bloodpressurediary.data.repository

import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.entity.User
import com.lenatopoleva.bloodpressurediary.data.model.HealthDataResult
import kotlinx.coroutines.channels.ReceiveChannel

interface IRepository<T> {
    fun getHealthData(): ReceiveChannel<HealthDataResult>
    suspend fun saveHealthData(healthData: HealthData): T
    suspend fun getHealthDataById(id: String): T
    suspend fun getCurrentUser(): User?
    suspend fun deleteHealthData(id: String): Unit
}