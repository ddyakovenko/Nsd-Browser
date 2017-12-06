package com.yakovenko.nsd.ui

import android.os.Bundle
import com.yakovenko.nsd.R
import com.yakovenko.nsd.di.module.MainModule
import com.yakovenko.nsd.net.NsdAssistant
import com.yakovenko.nsd.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    override val fragmentContainerId = R.id.container

    @Inject lateinit var assistant: NsdAssistant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment(ListFragment(), false)
    }

    override fun createScope() = super.createScope().also {
        it.installModules(MainModule())
    }

    override fun onResume() {
        super.onResume()
        assistant.discover()
    }

    override fun onPause() {
        super.onPause()
        assistant.stopDiscovering()
    }

    override fun onDestroy() {
        assistant.release()
        super.onDestroy()
    }
}