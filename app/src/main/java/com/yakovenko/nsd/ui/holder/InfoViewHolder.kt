package com.yakovenko.nsd.ui.holder

import android.view.ViewGroup
import android.widget.TextView
import com.yakovenko.nsd.R
import com.yakovenko.nsd.ui.base.view.ViewHolder
import java.lang.ref.WeakReference

class InfoViewHolder(
        parent: ViewGroup,
        listener: WeakReference<OnViewHolderClickListener>?
) : ViewHolder<ViewHolder.OnViewHolderClickListener>(parent, listener, R.layout.view_item_info, true) {
    val textView = itemView as TextView
}