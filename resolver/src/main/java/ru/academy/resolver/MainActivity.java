package ru.academy.resolver;

import android.database.Cursor;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URI_PATH = "content://ru.academy.provider/contact";
    private List<Contact> contactList;
    private ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(Uri.parse(URI_PATH), null, null, null, null);
        if (cursor != null) {
            contactList = new ArrayList<>();
            while (cursor.moveToNext()) {
                contactList.add(new Contact(cursor.getString(1), cursor.getString(2)));

            }
            cursor.close();
        }

        RecyclerView recyclerView = findViewById(R.id.itemList);
        itemListAdapter = new ItemListAdapter(contactList);
        recyclerView.setAdapter(itemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


    static class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
        private List<Contact> itemList;

        public ItemListAdapter(List<Contact> itemList) {
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
            Contact currentItem = itemList.get(position);
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
