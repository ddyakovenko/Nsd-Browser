package com.yakovenko.nsd.di.module

import android.content.Context
import android.net.nsd.NsdManager
import com.yakovenko.nsd.net.NsdAssistant
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        val manager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

        bind(Context::class.java).toInstance(context)
        bind(NsdManager::class.java).toInstance(manager)
        bind(NsdAssistant::class.java).to(NsdAssistant::class.java).singletonInScope()
    }
}