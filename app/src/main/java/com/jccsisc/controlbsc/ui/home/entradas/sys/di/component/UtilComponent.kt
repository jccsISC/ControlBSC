package com.jccsisc.controlbsc.ui.home.entradas.sys.di.component

import android.content.Context
import com.jccsisc.controlbsc.ui.home.entradas.sys.di.module.ModuleContext
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ModuleContext::class])
interface UtilComponent {
    fun getAppContext(): Context
}