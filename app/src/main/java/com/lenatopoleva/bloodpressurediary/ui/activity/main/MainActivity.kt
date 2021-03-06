package com.lenatopoleva.bloodpressurediary.ui.activity.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lenatopoleva.bloodpressurediary.R
import org.koin.android.ext.android.getKoin
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    companion object{
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    val navigatorHolder: NavigatorHolder by lazy { getKoin().get<NavigatorHolder>() }
    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    val model: MainViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) model.onCreate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        model.backPressed()
    }

}