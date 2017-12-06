package com.yakovenko.nsd.di.module

import com.yakovenko.nsd.presentation.Contract
import com.yakovenko.nsd.presentation.ListPresenter
import com.yakovenko.nsd.ui.ListFragment
import toothpick.config.Module

class ListModule(fragment: ListFragment) : Module() {
    init {
        bind(Contract.List.View::class.java).toInstance(fragment)
        bind(Contract.List.Presenter::class.java).to(ListPresenter::class.java)
    }
}