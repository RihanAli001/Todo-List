package com.rihanhack.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ItemList.class}, version = 2,exportSchema = false)
public abstract class ListItemDatabase extends RoomDatabase {
    public abstract ListDao userDao();
}
