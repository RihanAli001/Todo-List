package com.rihanhack.todolist.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoList")
public class ItemList {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "desc")
    public String desc;

    public ItemList(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
}
