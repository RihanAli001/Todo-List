package com.rihanhack.todolist.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ListDao {
    @Query("SELECT * FROM todoList")
    List<ItemList> getAll();

    @Query("SELECT * FROM todoList WHERE id IN (:userIds)")
    List<ItemList> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM todoList WHERE title LIKE :title AND " + "`desc` LIKE :desc LIMIT 1")
    ItemList findByName(String title, String desc);

    @Insert
    void insertItem(ItemList item);

    @Insert
    void insertAll(ItemList... item);

    @Delete
    void delete(ItemList item);
}
