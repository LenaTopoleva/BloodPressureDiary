package com.lenatopoleva.bloodpressurediary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lenatopoleva.bloodpressurediary.data.dispatcherprovider.DispatcherProvider
import com.lenatopoleva.bloodpressurediary.data.entity.HealthData
import com.lenatopoleva.bloodpressurediary.data.provider.DataProvider
import com.lenatopoleva.bloodpressurediary.data.provider.FirestoreDataProvider
import com.lenatopoleva.bloodpressurediary.data.repository.IRepository
import com.lenatopoleva.bloodpressurediary.data.repository.RepositoryImpl
import com.lenatopoleva.bloodpressurediary.ui.activity.main.MainViewModel
import com.lenatopoleva.bloodpressurediary.ui.activity.splash.SplashViewModel
import com.lenatopoleva.bloodpressurediary.ui.fragment.DiaryViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Provider

val application = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreDataProvider(get(), get()) } bind DataProvider::class
    single <IRepository<HealthData>>{ RepositoryImpl(get()) }
    single <DispatcherProvider> { DispatcherProvider() }
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>> {
        var map =
            mutableMapOf(
                SplashViewModel::class.java to Provider<ViewModel>{SplashViewModel(get())} ,
                MainViewModel::class.java to Provider<ViewModel>{ MainViewModel(get<Router>()) },
                DiaryViewModel::class.java to Provider<ViewModel>{DiaryViewModel(get<IRepository<HealthData>>() as RepositoryImpl, get<Router>()) },
            )
        map
    }
    single<ViewModelProvider.Factory> { ViewModelFactory(get())}
}

val navigation = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    factory<NavigatorHolder> { cicerone.navigatorHolder }
    factory<Router> { cicerone.router }
}

val splashActivity = module {
    factory { SplashViewModel(get()) }
}

val mainActivity = module {
    factory { MainViewModel(get<Router>()) }
}
val diaryFragment = module {
    factory { DiaryViewModel(get<IRepository<HealthData>>() as RepositoryImpl, get<Router>()) }
}