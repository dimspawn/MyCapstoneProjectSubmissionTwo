package com.imaginatic.mycapstoneprojectsubmissiontwo

import android.app.Application
import com.imaginatic.mycapstoneprojectsubmissiontwo.di.AppComponent
import com.imaginatic.mycapstoneprojectsubmissiontwo.di.DaggerAppComponent
import com.mycapstoneprojectsubmissiontwo.core.di.DaggerCoreComponent

open class MyApplication: Application() {
    private val coreComponent by lazy {
        DaggerCoreComponent.builder().context(this).build()
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().coreComponent(coreComponent).build()
    }
}