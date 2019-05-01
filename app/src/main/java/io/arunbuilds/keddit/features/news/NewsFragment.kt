package io.arunbuilds.keddit.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.arunbuilds.keddit.Common.InfiniteScrollListener
import io.arunbuilds.keddit.Common.RedditNews
import io.arunbuilds.keddit.Common.RxBaseFragment
import io.arunbuilds.keddit.Common.inflate
import io.arunbuilds.keddit.R
import io.arunbuilds.keddit.features.adapter.NewsAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.*

class NewsFragment : RxBaseFragment() {

    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    private var redditNews: RedditNews? = null

    private val newsManager by lazy { NewsManager() }

    private val newsList by lazy {
        news_list
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.news_fragment, container, false)
        return container?.inflate(R.layout.news_fragment)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        newsList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(linearLayout) { requestNews() })
        }


        if (newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (news_list.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
        //requestNews()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (news_list.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.size > 0) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestNews() {

        Log.d("Arun", "Requesting news with ${redditNews?.after ?: "Nothing bro first time"}")

        val subscription = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io())
            .subscribe(
                { retrievedNews ->
                    redditNews = retrievedNews
                    (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
                    Log.d("Arun", retrievedNews.toString())

                },
                { e ->
                    Snackbar.make(newsList, e.message ?: "", Snackbar.LENGTH_LONG).show()
                    Log.d("Arun", "Error ${e.message}")
                }
            )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {


    }
}




