package com.yakovenko.nsd.presentation

import com.yakovenko.nsd.extension.weak

abstract class Presenter<out V : PresentationView>(view: V) {
    private val reference = view.weak()
    protected val view: V? get() = reference.get()
}