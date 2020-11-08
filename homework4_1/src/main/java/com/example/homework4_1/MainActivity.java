package com.example.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ItemListAdapter itemListAdapter;
    final private String POSITION = "POSITION";


    private static final List<Item> itemList;

    static {
        itemList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            itemList.add(new Item("Name", "+375 (29) xxx-xx-xx"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.itemList);
        itemListAdapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(itemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.addIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent, 1000);
            }
        });

        itemListAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent2 = new Intent(MainActivity.this, EditContact.class);
                intent2.putExtra(POSITION, position);
                startActivityForResult(intent2, 2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(MainActivity.this, "Новый контакт добавлен", Toast.LENGTH_SHORT).show();
            itemList.add(0, new Item(data.getStringExtra("SAVEADDNAME"), data.getStringExtra("SAVEADDNUMBER")));
            itemListAdapter.notifyDataSetChanged();

        } else if (requestCode == 2000 && resultCode == Activity.RESULT_OK && data != null) {
            Toast.makeText(MainActivity.this, "Контакт изменен", Toast.LENGTH_SHORT).show();
            itemList.set(data.getIntExtra("POSITION", 0), new Item(data.getStringExtra("SAVEEDITNAME"), data.getStringExtra("SAVEEDITNUMBER")));
            itemListAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    static class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

        private List<Item> items;
        public ItemListAdapter(List<Item> items) {
            this.items = items;
        }
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number_title, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view, mListener);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bind(position);
            Item currentItem = itemList.get(position);
            holder.textViewName.setText(currentItem.getName());
            holder.phoneView.setText(currentItem.getPhone());
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }


        static class ItemViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView phoneView;
            private TextView textViewName;
            private TextView textNumberView;

            public ItemViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
                super(itemView);
                textNumberView = itemView.findViewById(R.id.numberView);
                imageView = itemView.findViewById(R.id.viewIcon);
                phoneView = itemView.findViewById(R.id.phoneView);
                textViewName = itemView.findViewById(R.id.nameView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            listener.onItemClick(position);
                        }
                    }
                });
            }

            public void bind(final int number) {
                textNumberView.setText(String.valueOf(number + 1));
                imageView.setImageResource(R.drawable.contact);
            }
        }
    }
}