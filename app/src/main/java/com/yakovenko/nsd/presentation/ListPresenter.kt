package com.yakovenko.nsd.presentation

import com.yakovenko.nsd.repository.NsdInfoRepository
import com.yakovenko.nsd.repository.Repository
import javax.inject.Inject

class ListPresenter @Inject constructor(
        view: Contract.List.View,
        override val repository: NsdInfoRepository
) : Presenter<Contract.List.View>(view), Contract.List.Presenter, Repository.Callback {

    init {
        repository.subscribe(this)
    }

    override fun get(position: Int) = repository.provider.items.getOrNull(position)

    override fun onItemInserted(position: Int) {
        view?.onItemInserted(position)
    }

    override fun onItemRemoved(position: Int) {
        view?.onItemRemoved(position)
    }

    override fun onDataChanged(payload: Int) = Unit
}
