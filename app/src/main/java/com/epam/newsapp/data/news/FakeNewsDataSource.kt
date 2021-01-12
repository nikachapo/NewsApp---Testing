package com.epam.newsapp.data.news

import com.epam.newsapp.data.news.model.NewsModel

class FakeNewsDataSource : NewsDataSource {

    override suspend fun getNews(): List<NewsModel> {
        return news
    }

    companion object {
        private val news = mutableListOf(
            NewsModel(
                "title 1",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                124523536457346
            ),
            NewsModel(
                "title 2",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                4564564545745
            ),
            NewsModel(
                "title 3",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                645645754754
            ),NewsModel(
                "title 4",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                86534534534534
            ),NewsModel(
                "title 5",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                367567566435453
            ),NewsModel(
                "title 6",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                56534647657543
            ),NewsModel(
                "title 7",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                345745745745
            ),NewsModel(
                "title 8",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                4573457547457
            ),NewsModel(
                "title 9",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                85685865646
            ),NewsModel(
                "title 10",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                23456475673465
            ),NewsModel(
                "title 11",
                "Create app that has 2 screens: Login and News screen, in MVVM pattern. At the beginning user goes to Login screen. After login user get some kind of access token. While user has valid access token user won't see login page when he opens the app one more time(immediately redirect to News screen). Token is live for 10min. NOTE: You won't have API for it. You will need to imitate it in your code.",
                32908546754547
            )
        )
    }
}