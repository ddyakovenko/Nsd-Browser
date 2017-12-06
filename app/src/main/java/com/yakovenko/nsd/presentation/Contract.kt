package com.yakovenko.nsd.presentation

import android.net.nsd.NsdServiceInfo
import com.yakovenko.nsd.repository.NsdInfoRepository

interface Contract {

    interface List {
        interface View : UpdatableView
        interface Presenter {
            val repository: NsdInfoRepository
            fun get(position: Int): NsdServiceInfo?
        }
    }
}