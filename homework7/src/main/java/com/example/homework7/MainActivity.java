package com.example.homework7;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ItemListAdapter itemListAdapter;
    final private String POSITION = "POSITION";
    private AppDatabase db;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        itemList = db.contactDao().getAll();
        RecyclerView recyclerView = findViewById(R.id.itemList);
        itemListAdapter = new ItemListAdapter(itemList);
        recyclerView.setAdapter(itemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.addIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });

        itemListAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String name, String data) {
                Intent intent2 = new Intent(MainActivity.this, EditContact.class);
                intent2.putExtra(POSITION, position);
                intent2.putExtra("NAME", name);
                intent2.putExtra("DATA", data);
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void onResume() {
        itemList = db.contactDao().getAll();
        super.onResume();
    }

    static class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
        private List<Item> itemList;

        public ItemListAdapter(List<Item> itemList) {
            this.itemList = itemList;
        }

        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(int position, String name, String data);
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
            holder.phoneView.setText(currentItem.getData());
        }

        @Override
        public int getItemCount() {
            return itemList != null ? itemList.size() : 0;
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
                            String name = textViewName.getText().toString();
                            String data = phoneView.getText().toString();
                            listener.onItemClick(position, name, data);
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
