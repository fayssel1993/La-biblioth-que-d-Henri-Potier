package com.yabasoft.henripotier

import android.app.Application
import com.yabasoft.henripotier.di.*
import org.koin.android.ext.android.startKoin

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //add module to inject
        startKoin(
            this,
            modules = listOf(
                networkModule,
                apiModule,
                viewModelModule,
                databaseModule,
                repositoryModule
            )
        )
    }
}