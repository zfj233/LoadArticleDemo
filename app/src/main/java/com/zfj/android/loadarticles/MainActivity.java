package com.zfj.android.loadarticles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;
import com.zfj.android.loadarticles.database.DataBaseHelper;
import com.zfj.android.loadarticles.database.GetDao;
import com.zfj.android.loadarticles.datas.Article;
import com.zfj.android.loadarticles.datas.DBFavorData;
import com.zfj.android.loadarticles.network.LoadingAsyncHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String BASE_URL = "http://www.imooc.com/api/teacher?type=3&cid=";
    private TextView articleTitle;
    private TextView articleAuthor;
    private TextView articleContent;
    private TextView myFavorTxt;
    private ImageView netImg;
    private Button addFavorBtn;
    private Button preBtn;
    private Button nextBtn;
    private int indexPage = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadArticle();
        bindEvents();
    }

    private void bindEvents() {
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断页数如果为1，则重置为30
                if (indexPage == 1) {
                    indexPage = 30;
                } else {
                    indexPage--;
                }

                loadArticle();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断页数如果为30，则重置为1
                if (indexPage == 30) {
                    indexPage = 1;
                } else {
                    indexPage++;
                }

                loadArticle();
            }
        });
        myFavorTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrmliteDemo.class));
            }
        });

        addFavorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<DBFavorData> favorList = GetDao.get(MainActivity.this).queryForAll();
                    for (int i = 0; i < favorList.size(); i++) {
                        if (articleTitle.getText().toString().equals(favorList.get(i).getTitle())) {
                            Toast.makeText(MainActivity.this, R.string.have_been, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    DBFavorData tempData = new DBFavorData(articleTitle.getText().toString(),
                            articleAuthor.getText().toString(),
                            articleContent.getText().toString());
                    GetDao.get(MainActivity.this).create(tempData);
                    Toast.makeText(MainActivity.this, R.string.success_add_favor, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 网络请求回调接口，用来将得到的数据加载到控件
     */
    private void loadArticle() {
        new LoadingAsyncHelper().requestAritcle(BASE_URL + String.valueOf(indexPage),
                new LoadingAsyncHelper.onRequestListener() {
                    @Override
                    public void onSuccess(Article article) {
                        String title = article.getTitle();
                        String author = article.getAuthor();
                        String content = article.getContent();
                        articleTitle.setText(title);
                        articleAuthor.setText(author);
                        articleContent.setText(content);
                    }

                    @Override
                    public void onFail(String msg) {
                        Log.e(TAG, msg);
                    }
                });

    }

    private void initView() {
        articleTitle = (TextView) findViewById(R.id.article_title);
        articleAuthor = (TextView) findViewById(R.id.article_author);
        articleContent = (TextView) findViewById(R.id.article_content);
        myFavorTxt = (TextView) findViewById(R.id.my_favor);
        netImg = (ImageView) findViewById(R.id.net_img);
        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        //家在网络图片
        Picasso.with(this)
                .load("http://img.mukewang.com/546418750001a81906000338-590-330.jpg")
                .resize(metric.widthPixels, 300)
                .centerCrop()
                .into(netImg);
        preBtn = (Button) findViewById(R.id.pre_article);
        nextBtn = (Button) findViewById(R.id.next_article);
        addFavorBtn = (Button) findViewById(R.id.add_favor);
    }

}
