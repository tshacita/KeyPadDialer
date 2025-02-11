package keypaddialer

import android.app.Application
import android.util.Log
import keypaddialer.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KeyPadApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            startKoin {
                androidContext(this@KeyPadApplication)
                modules(modules = appModules)
                androidLogger()
            }
            Log.d("KeyPadApplication", "Koin started successfully")
        } catch (e: Exception) {
            Log.e("KeyPadApplication", "Koin initialization failed", e)
        }
    }
}