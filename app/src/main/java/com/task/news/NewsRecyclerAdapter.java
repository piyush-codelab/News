package com.task.news;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{

    List<Article> articleList;
    NewsRecyclerAdapter(List<Article>articleList){
        this.articleList = articleList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
        return new NewsViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourcesTextView.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.baseline_image_not_supported_24)
                .placeholder(R.drawable.baseline_image_not_supported_24)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NewFullActivity.class);
            intent.putExtra("url",article.getUrl());
            view.getContext().startActivity(intent);
        });

    }

    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class  NewsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView,sourcesTextView;
        ImageView imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.article_title);
            sourcesTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.artic_img_view);
        }
    }
}
