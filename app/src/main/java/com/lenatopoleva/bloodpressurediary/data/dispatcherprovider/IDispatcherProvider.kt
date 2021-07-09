package com.lenatopoleva.bloodpressurediary.data.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    fun io(): CoroutineDispatcher
}