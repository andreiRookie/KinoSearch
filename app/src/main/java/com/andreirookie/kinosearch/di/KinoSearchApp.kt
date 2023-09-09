package com.andreirookie.kinosearch.di

import android.app.Application
import android.content.Context

class KinoSearchApp : Application() {

    private var _appComponent: AppComponent? = null
    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent is null"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() {
        return when (this) {
            is KinoSearchApp -> appComponent
            else -> this.applicationContext.appComponent
        }
    }