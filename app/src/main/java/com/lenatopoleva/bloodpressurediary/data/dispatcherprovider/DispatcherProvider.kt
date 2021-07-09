package com.lenatopoleva.bloodpressurediary.data.dispatcherprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider: IDispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}