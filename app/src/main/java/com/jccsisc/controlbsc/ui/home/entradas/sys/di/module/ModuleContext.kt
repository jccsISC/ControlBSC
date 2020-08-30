package com.jccsisc.controlbsc.ui.home.entradas.sys.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ModuleContext @Inject constructor(private val context: Context){

    @Provides
    fun providesContext(): Context {return context}
}