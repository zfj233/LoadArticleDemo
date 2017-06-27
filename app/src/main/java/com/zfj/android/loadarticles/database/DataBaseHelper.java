package com.zfj.android.loadarticles.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.zfj.android.loadarticles.datas.DBFavorData;

import java.sql.SQLException;

/**
 * Created by zfj_ on 2017/6/11.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper
{
    private DataBaseHelper(Context context) {
        super(context, "my_favor.db", null, 1);
    }
    private static DataBaseHelper sDataBaseHelper = null;
    public static synchronized DataBaseHelper getInstance(Context context){
        if(sDataBaseHelper == null){
            sDataBaseHelper = new DataBaseHelper(context);
        }
        return sDataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DBFavorData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,DBFavorData.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
