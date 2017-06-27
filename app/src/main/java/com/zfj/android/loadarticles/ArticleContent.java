package com.zfj.android.loadarticles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.zfj.android.loadarticles.database.DataBaseHelper;
import com.zfj.android.loadarticles.database.GetDao;
import com.zfj.android.loadarticles.datas.DBFavorData;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zfj_ on 2017/6/11.
 */

public class ArticleContent extends Activity {
    private TextView contentTitle;
    private TextView contentAuthor;
    private TextView contentContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_article);
        initView();
        LoadDatas();
    }

    /**
     * 加载数据
     */
    private void LoadDatas() {
        try {
            Dao<DBFavorData, Integer> dao = GetDao.get(this);
            List<DBFavorData> dbFavorDatas = dao.queryForAll();
            //得到单击的item的Title
            String getTitle = getIntent().getStringExtra("titleName");
            DBFavorData newDatas = null;
            for(int i = 0;i<dbFavorDatas.size();i++){
                if(getTitle.equals(dbFavorDatas.get(i).getTitle())){
                    newDatas = dbFavorDatas.get(i);
                    break;
                }
            }
            if(newDatas!=null){
                contentTitle.setText(newDatas.getTitle());
                contentAuthor.setText(newDatas.getAuthor());
                contentContent.setText(newDatas.getContent());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        contentAuthor = (TextView) findViewById(R.id.content_author);
        contentTitle = (TextView) findViewById(R.id.content_title);
        contentContent = (TextView) findViewById(R.id.content_content);

    }
}
