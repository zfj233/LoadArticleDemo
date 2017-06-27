package com.zfj.android.loadarticles.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.zfj.android.loadarticles.datas.DBFavorData;

import java.sql.SQLException;

/**
 * Created by zfj_ on 2017/6/11.
 */

/**
 * 获取Dao，多处用到
 */
public class GetDao {

    public static Dao<DBFavorData, Integer> get(Context context) throws SQLException {

        return DataBaseHelper.getInstance(context).getDao(DBFavorData.class);

    }
}
