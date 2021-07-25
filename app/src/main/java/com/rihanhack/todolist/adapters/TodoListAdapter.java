package com.rihanhack.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.rihanhack.todolist.MainActivity;
import com.rihanhack.todolist.R;
import com.rihanhack.todolist.TodoListViewDetail;
import com.rihanhack.todolist.data.ItemList;
import com.rihanhack.todolist.data.ListItemDatabase;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    private List<ItemList> list;
    private Context context;

    public TodoListAdapter(List<ItemList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_list_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.ViewHolder holder, int position) {
        int id = list.get(position).id;
        String title = list.get(position).title;
        String desc = list.get(position).desc;
        try {

            holder.title.setText(list.get(position).title);
        }catch (Exception e){e.printStackTrace();}

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TodoListViewDetail.class);
            intent.putExtra("id",id);
            intent.putExtra("title",title);
            intent.putExtra("desc",desc);
            context.startActivity(intent);
        });

        holder.cardView.setOnLongClickListener(v -> {
            ListItemDatabase database = Room.databaseBuilder(context, ListItemDatabase.class, "Todo1DB").allowMainThreadQueries().build();
            ItemList itemList = new ItemList(title,desc);
            itemList.id = id;

            database.userDao().delete(itemList);
            try {
                list.remove(position);
                MainActivity.adapter.notifyDataSetChanged();
                Toast.makeText(context, "Removed : "+title, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(context, "Can't remove : "+title, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            cardView = itemView.findViewById(R.id.onClickToDetail);
        }
    }
}
