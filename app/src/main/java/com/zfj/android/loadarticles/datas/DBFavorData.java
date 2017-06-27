package com.zfj.android.loadarticles.datas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zfj_ on 2017/6/11.
 */

/**
 * 数据库实体类
 */
@DatabaseTable(tableName = "tb_favor_article")
public class DBFavorData {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(uniqueCombo = true)
    private String title;
    @DatabaseField
    private String author;
    @DatabaseField
    private String content;

    public DBFavorData(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public DBFavorData() {

    }
}
