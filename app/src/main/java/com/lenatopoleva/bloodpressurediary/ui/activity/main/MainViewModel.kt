package com.lenatopoleva.bloodpressurediary.ui.activity.main

import androidx.lifecycle.ViewModel
import com.lenatopoleva.bloodpressurediary.navigation.Screens
import com.lenatopoleva.bloodpressurediary.ui.base.BaseViewModel
import ru.terrakok.cicerone.Router

class MainViewModel (private val router: Router): ViewModel(){

    fun backPressed() {
        router.exit()
    }

    fun onCreate() {
        router.replaceScreen(Screens.DiaryScreen())
    }

}