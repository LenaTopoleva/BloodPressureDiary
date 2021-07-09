package com.lenatopoleva.bloodpressurediary.data.provider

import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.entity.User
import com.lenatopoleva.bloodpressurediary.data.model.HealthDataResult
import kotlinx.coroutines.channels.ReceiveChannel

interface DataProvider {
    fun subscribeToHealthData(): ReceiveChannel<HealthDataResult>
    suspend fun saveHealthData(healthData: HealthData): HealthData
    suspend fun deleteHealthData(id: String)
    suspend fun getHealthDataById(id: String): HealthData
    suspend fun getCurrentUser(): User?
}