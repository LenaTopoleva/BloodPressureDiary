package com.lenatopoleva.bloodpressurediary.data.repository

import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.entity.User
import com.lenatopoleva.bloodpressurediary.data.model.HealthDataResult
import com.lenatopoleva.bloodpressurediary.data.provider.DataProvider
import kotlinx.coroutines.channels.ReceiveChannel

class RepositoryImpl(val dataProvider: DataProvider): IRepository<HealthData> {
    override fun getHealthData(): ReceiveChannel<HealthDataResult> =
        dataProvider.subscribeToHealthData()

    override suspend fun saveHealthData(healthData: HealthData) =
        dataProvider.saveHealthData(healthData)

    override suspend fun getHealthDataById(id: String): HealthData =
        dataProvider.getHealthDataById(id)

    override suspend fun getCurrentUser(): User? = dataProvider.getCurrentUser()

    override suspend fun deleteHealthData(id: String) = dataProvider.deleteHealthData(id)
}