package io.arunbuilds.keddit.features.news

import io.arunbuilds.keddit.Common.RedditNews
import io.arunbuilds.keddit.Common.RedditNewsItem
import io.arunbuilds.keddit.api.RestAPI
import io.reactivex.Observable

/**
 * News Manager allows you to request news from Reddit API.
 *
 */
class NewsManager(private val api: RestAPI = RestAPI()) {

    /**
     *
     * Returns Reddit News paginated by the given limit.
     *
     * @param after indicates the next page to navigate.
     * @param limit the number of news to request.
     */
    fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body()!!.data
                val news = dataResponse.children.map {
                    val item = it.data
                    RedditNewsItem(
                        item.author, item.title, item.num_comments,
                        item.created, item.thumbnail, item.url
                    )
                }
                val redditNews = RedditNews(
                    dataResponse.after ?: "",
                    dataResponse.before ?: "",
                    news
                )

                subscriber.onNext(redditNews)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}