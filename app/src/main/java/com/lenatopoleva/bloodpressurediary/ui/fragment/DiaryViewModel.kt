package com.lenatopoleva.bloodpressurediary.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.model.HealthDataResult
import com.lenatopoleva.bloodpressurediary.data.repository.RepositoryImpl
import com.lenatopoleva.bloodpressurediary.ui.base.BaseViewModel
import com.lenatopoleva.bloodpressurediary.utils.livedataevent.Event
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router

@ObsoleteCoroutinesApi
class DiaryViewModel(val repository: RepositoryImpl, val router: Router):
    BaseViewModel<List<HealthData>?>() {

    private val _openDialogFragmentLiveData = MutableLiveData<Event<HealthData?>>()

    private val repositoryHealthData = repository.getHealthData()
    val openDialogFragmentLiveData: LiveData<Event<HealthData?>>
        get() = _openDialogFragmentLiveData

    init {
        launch {
            repositoryHealthData.consumeEach { result ->
                when (result){
                    is HealthDataResult.Success<*> -> setData(result.data as? List<HealthData>)
                    is HealthDataResult.Error -> setError(result.error)
                }
            }
        }
    }

    @ObsoleteCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        repositoryHealthData.cancel()
    }

    fun dialogFragmentBtnYesClicked(healthData: HealthData) {
        launch {
            repository.saveHealthData(healthData)
        }
    }

    fun dialogFragmentBtnCancelClicked() {}

    fun fabClicked() {
        _openDialogFragmentLiveData.value = Event(null)
    }

    fun listItemClicked(healthData: HealthData) {
        _openDialogFragmentLiveData.value = Event<HealthData?>(healthData)
    }

}
