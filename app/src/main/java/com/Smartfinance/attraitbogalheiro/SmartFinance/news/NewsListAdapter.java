package com.Smartfinance.attraitbogalheiro.SmartFinance.news;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.Smartfinance.attraitbogalheiro.SmartFinance.views.NewsView;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.holder> {

    private List<NewsItem> newsList;

    public NewsListAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    public NewsListAdapter setData(List<NewsItem> newsList){
        this.newsList = newsList;
        this.notifyDataSetChanged();
        return this;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(new NewsView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        holder.getView().setData(newsList.get(position));
    }

    public static class holder extends RecyclerView.ViewHolder {

        private holder(NewsView itemLayoutView) {
            super(itemLayoutView);
        }

        public NewsView getView(){
            return (NewsView) itemView;
        }

    }

    public int getItemCount() {
        return newsList.size();
    }
}
