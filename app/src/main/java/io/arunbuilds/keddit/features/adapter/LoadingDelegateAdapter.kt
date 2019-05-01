package io.arunbuilds.keddit.features.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.arunbuilds.keddit.Common.adapter.ViewType
import io.arunbuilds.keddit.Common.adapter.ViewTypeDelegateAdapter
import io.arunbuilds.keddit.Common.inflate
import io.arunbuilds.keddit.R

class LoadingDelegateAdapter : ViewTypeDelegateAdapter{
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item_loading)) {
    }
}