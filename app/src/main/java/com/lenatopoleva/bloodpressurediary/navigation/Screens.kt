package com.lenatopoleva.bloodpressurediary.navigation

import com.lenatopoleva.bloodpressurediary.ui.fragment.DiaryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class DiaryScreen() : SupportAppScreen() {
        override fun getFragment() = DiaryFragment.newInstance()
    }
}