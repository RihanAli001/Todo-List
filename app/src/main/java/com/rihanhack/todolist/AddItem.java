package com.rihanhack.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputEditText;
import com.rihanhack.todolist.data.ItemList;
import com.rihanhack.todolist.data.ListItemDatabase;
import java.util.List;
import java.util.Objects;

public class AddItem extends AppCompatActivity {
    private EditText title;
    private TextInputEditText description;
    private Button save_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        title = findViewById(R.id.item_title);
        description = findViewById(R.id.item_description);

        save_item = findViewById(R.id.save_item);
        save_item.setOnClickListener(v -> addData(title.getText().toString(), Objects.requireNonNull(description.getText()).toString()));
    }

    public void addData(String title,String desc){
        new Thread(){
            @Override
            public void run() {
                super.run();
                ListItemDatabase database = Room.databaseBuilder(getApplicationContext(), ListItemDatabase.class, "Todo1DB").allowMainThreadQueries().build();
                database.userDao().insertItem(new ItemList(title,desc));
                List<ItemList> itemLists = database.userDao().getAll();
                try {
                    MainActivity.items.clear();
                    MainActivity.items.addAll(itemLists);
                    MainActivity.adapter.notifyDataSetChanged();
                }catch (Exception e){e.printStackTrace();}
                finish();
            }
        }.start();
    }
}