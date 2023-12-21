package com.task.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;

    LinearProgressIndicator progressIndicator;

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;

     SearchView searchView;

     ImageView moreinfor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progressbar);
        searchView = findViewById(R.id.search_view);
        moreinfor = findViewById(R.id.moreinfo);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        moreinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MoreinfoActivity.class);
                startActivity(intent);
            }
        });
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
       getNews("General",query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
});



        setupRecyclerView();
        getNews("General",null);
    }

    void setupRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);


    }

    void changeInProgress(boolean show){
        if (show){
            progressIndicator.setVisibility(View.VISIBLE);
        }else {
            progressIndicator.setVisibility(View.INVISIBLE);

        }    }
    void getNews(String category,String query){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("7ce31fadb36d4f10a053f97e0edd8ae7");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .q(query)
                        .category(category)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                       // Log.i("GOT RESPONSE", response.toString());
                       /* response.getArticles().forEach((a)->{
                            Log.i("Article", a.getTitle() );

                        });*/

                        runOnUiThread(()->{
                            changeInProgress(false);
                            articleList =response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("GOT No RESPONSE", throwable.getMessage());

                    }
                }

        );
    }

    @Override
    public void onClick(View view) {
        Button btn =(Button) view;
        String category = btn.getText().toString();
        getNews(category,null);

    }
}