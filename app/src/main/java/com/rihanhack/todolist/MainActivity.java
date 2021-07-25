package com.rihanhack.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.rihanhack.todolist.adapters.TodoListAdapter;
import com.rihanhack.todolist.data.ItemList;
import com.rihanhack.todolist.data.ListItemDatabase;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView addItem;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public static TodoListAdapter adapter;
    public static ArrayList<ItemList> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.item_recycle_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Database using room
        ListItemDatabase database = Room.databaseBuilder(getApplicationContext(), ListItemDatabase.class, "Todo1DB").allowMainThreadQueries().build();
        List<ItemList> itemLists = database.userDao().getAll();


        items = new ArrayList<>();
        items.addAll(itemLists);

        new Thread(){
            @Override
            public void run() {
                super.run();
                adapter = new TodoListAdapter(items,MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }.start();

        addItem = findViewById(R.id.additem);
        addItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AddItem.class);
            startActivity(intent);
        });
    }
}