package com.zfj.android.loadarticles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.DatabaseTable;
import com.zfj.android.loadarticles.adapters.FavorListAdapter;
import com.zfj.android.loadarticles.database.DataBaseHelper;
import com.zfj.android.loadarticles.database.GetDao;
import com.zfj.android.loadarticles.datas.DBFavorData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zfj_ on 2017/6/11.
 */

public class OrmliteDemo extends Activity {
    private ListView favorListView;
    private List<String> favorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favor);
        initView();
        bindEvents();
    }

    private void bindEvents() {
        favorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrmliteDemo.this, ArticleContent.class);
                intent.putExtra("titleName", favorTitle.get(position));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        favorListView = (ListView) findViewById(R.id.favor_list);
        favorListView.setAdapter(new FavorListAdapter(this, getListFavor()));

    }

    /**
     * 获取收藏的文章的列表
     * @return
     */
    public List<String> getListFavor() {
        try {
            Dao<DBFavorData, Integer> favorDao =
                    GetDao.get(this);
            List<DBFavorData> favorList = favorDao.queryForAll();
            favorTitle = new ArrayList<>();
            for (int i = 0; i < favorList.size(); i++) {
                favorTitle.add(favorList.get(i).getTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorTitle;
    }
}
