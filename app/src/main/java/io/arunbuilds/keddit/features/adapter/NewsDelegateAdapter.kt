package io.arunbuilds.keddit.features.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.arunbuilds.keddit.Common.RedditNewsItem
import io.arunbuilds.keddit.Common.adapter.ViewType
import io.arunbuilds.keddit.Common.adapter.ViewTypeDelegateAdapter
import io.arunbuilds.keddit.Common.getFriendlyTime
import io.arunbuilds.keddit.Common.inflate
import io.arunbuilds.keddit.Common.loadImg
import io.arunbuilds.keddit.R
import kotlinx.android.synthetic.main.news_item.view.*

class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item)) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RedditNewsItem) = with(itemView) {
            //Picasso.with(itemView.context).load(item.thumbnail).into(img_thumbnail)
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }
}