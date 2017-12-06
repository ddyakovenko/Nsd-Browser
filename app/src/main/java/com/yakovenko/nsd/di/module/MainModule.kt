package com.yakovenko.nsd.di.module

import com.yakovenko.nsd.repository.NsdInfoRepository
import toothpick.config.Module

class MainModule : Module() {
    init {
        bind(NsdInfoRepository::class.java).singletonInScope()
    }
}