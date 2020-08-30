package com.jccsisc.controlbsc.ui.home.entradas.sys.di

import android.app.Application
import android.content.Context
import com.jccsisc.controlbsc.ui.home.entradas.sys.di.component.*
import com.jccsisc.controlbsc.ui.home.entradas.sys.di.module.*

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        utilComponent = DaggerUtilComponent.builder().moduleContext(ModuleContext(applicationContext)).build()
    }

    companion object {
        lateinit var utilComponent: UtilComponent

        fun getAppContext(): Context {
            return utilComponent.getAppContext()
        }

    }
}