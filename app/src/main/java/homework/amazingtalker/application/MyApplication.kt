package homework.amazingtalker.application

import android.app.Application
import homework.amazingtalker.di.ApplicationComponent
import homework.amazingtalker.di.DaggerApplicationComponent

class MyApplication : Application() {
    val appComponent = DaggerApplicationComponent.create()
}