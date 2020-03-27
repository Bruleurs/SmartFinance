package com.Smartfinance.attraitbogalheiro.SmartFinance.news;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.Smartfinance.attraitbogalheiro.SmartFinance.R;
import com.Smartfinance.attraitbogalheiro.SmartFinance.models.rest.News;
import com.Smartfinance.attraitbogalheiro.SmartFinance.rest.NewsService;
import com.grizzly.rest.Model.RestResults;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.Smartfinance.attraitbogalheiro.SmartFinance.R.color.colorAccent;




public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private NewsListAdapter adapter;
    private RecyclerView recyclerView;
    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Observable newsObservable;

    
    public void getNewsObservable(int whatToDo, boolean cache){

        Action1<News[]> subscriber = new Action1<News[]>() {
            @Override
            public void call(News[] newsRestResults) {

                List<NewsItem> myNews = new ArrayList<>();
                if(newsRestResults != null && newsRestResults.length > 0){

                    Parcelable recyclerViewState;
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    for(News news: newsRestResults){
                        NewsItem newsItem = new NewsItem(news.getTitle(),
                                news.getUrl(), news.getBody(),
                                news.getImageurl(), news.getSource(),
                                news.getPublishedOn());
                        if(!myNews.contains(newsItem)) myNews.add(newsItem);
                    }
                    adapter.setData(myNews);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    Log.e("News", "call successful");
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("News", "call failed");
                }
            }
        };

        if (whatToDo == 2) {//Observable instance from EasyRest
            if (newsObservable == null) {
                newsObservable = NewsService.getPlainObservableNews(this, cache).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
            newsObservable.subscribe(subscriber);
        } else {
            NewsService.getObservableNews(this, true, new Action1<RestResults<News[]>>() {
                @Override
                public void call(RestResults<News[]> newsRestResults) {
                    Parcelable recyclerViewState;
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    if (newsRestResults.isSuccessful()) {
                        List<NewsItem> myNews = new ArrayList<>();
                        for (News news : newsRestResults.getResultEntity()) {
                            NewsItem newsItem = new NewsItem(news.getTitle(),
                                    news.getUrl(), news.getBody(),
                                    news.getImageurl(), news.getSource(),
                                    news.getPublishedOn());
                            if (!myNews.contains(newsItem)) myNews.add(newsItem);
                        }
                        adapter.setData(myNews);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        mToolbar = findViewById(R.id.toolbar_news_list);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.News));
        mActivity = this;
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_recycler);
        recyclerView = findViewById(R.id.newsListRecyclerView);
        HorizontalDividerItemDecoration divider = new HorizontalDividerItemDecoration.Builder(this).build();
        recyclerView.addItemDecoration(divider);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeResources(colorAccent);
        adapter = new NewsListAdapter(new ArrayList<NewsItem>());
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getNewsObservable(2, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.news_refresh_button) {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }
        finish();
        return true;
    }

}
