package ru.sicampus.bootcamp2026


import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.sicampus.bootcamp2026.di.appModule

class TravoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@TravoApplication)
            modules(appModule)
        }
    }
}