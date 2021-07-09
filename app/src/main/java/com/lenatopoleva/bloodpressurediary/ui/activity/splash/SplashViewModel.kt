package com.lenatopoleva.bloodpressurediary.ui.activity.splash

import com.lenatopoleva.bloodpressurediary.data.errors.NoAuthException
import com.lenatopoleva.bloodpressurediary.data.repository.IRepository
import com.lenatopoleva.bloodpressurediary.ui.base.BaseViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

class SplashViewModel (val repository: IRepository<Any>) : BaseViewModel<Boolean>() {

    @ObsoleteCoroutinesApi
    fun requestUser() = launch {
        repository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }

}