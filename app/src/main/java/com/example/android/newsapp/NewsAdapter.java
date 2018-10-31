package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;
    private LayoutInflater mInflater;
    private NewsClickListener newsClickListener;

    public NewsAdapter(Context context, List<News> newsList){
        this.mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
        this.newsClickListener = null;
    }

    public NewsAdapter(Context context, List<News> newsList, NewsClickListener newsClickListener){
        this.mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
        this.newsClickListener = newsClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.news, viewGroup, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final News currentNews = newsList.get(i);

        viewHolder.myNewsImage.setImageResource(currentNews.getNewsImageId());
        viewHolder.myNewsTitle.setText(currentNews.getNewsTitle());
        //Put "..." if the text is too big
        viewHolder.myNewsTitle.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.myNewsSection.setText(currentNews.getNewsSection());
        if(currentNews.getNewsData() != null) {
            viewHolder.myNewsData.setText(currentNews.getNewsData());
            viewHolder.myNewsAuthor.setText(currentNews.getNewsAuthor());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newsClickListener != null){
                    newsClickListener.onNewsSelected(currentNews);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myNewsImage;
        TextView myNewsTitle;
        TextView myNewsSection;
        TextView myNewsData;
        TextView myNewsAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myNewsImage = itemView.findViewById(R.id.newsImage);
            myNewsTitle = itemView.findViewById(R.id.newsTitle);
            myNewsSection = itemView.findViewById(R.id.newsSection);
            myNewsData = itemView.findViewById(R.id.newsData);
            myNewsAuthor = itemView.findViewById(R.id.newsAuthor);
        }
    }

    public void clear() {
        final int size = newsList.size();
        newsList.clear();
        notifyItemRangeRemoved(0, size);
    }


}
