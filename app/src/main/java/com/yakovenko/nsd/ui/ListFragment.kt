package com.yakovenko.nsd.ui

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.yakovenko.nsd.R
import com.yakovenko.nsd.di.module.ListModule
import com.yakovenko.nsd.presentation.Contract
import com.yakovenko.nsd.ui.base.BaseListFragment
import com.yakovenko.nsd.ui.base.adapter.RecyclerAdapter
import com.yakovenko.nsd.ui.base.view.ViewHolder
import com.yakovenko.nsd.ui.delegate.InfoAdapterDelegate
import javax.inject.Inject

class ListFragment : BaseListFragment(), Contract.List.View, ViewHolder.OnViewHolderClickListener {

    override val layoutResId = R.layout.fragment_list
    override val toolbarTitleRes = R.string.app_name

    @Inject lateinit var presenter: Contract.List.Presenter

    override fun createScope() = super.createScope().also {
        it.installModules(ListModule(this))
    }

    override fun createAdapter(): RecyclerAdapter {
        val provider = presenter.repository.provider
        return RecyclerAdapter(listOf(InfoAdapterDelegate(provider, null)), provider)
    }

    override fun configure(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}