package ru.academy.provider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditContact extends AppCompatActivity {
    private Switch switch2;
    private boolean ifChecked;
    private String name;
    private String data;
    private int position;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        final EditText editName = findViewById(R.id.editName);
        final EditText editData = findViewById(R.id.editPhone);
        Intent intentPosition = getIntent();
        position = intentPosition.getIntExtra("POSITION", 0);
        name = intentPosition.getStringExtra("NAME");
        data = intentPosition.getStringExtra("DATA");
        editName.setHint(name);
        editData.setHint(data);
        db = AppDatabase.getInstance(this);

        switch2 = findViewById(R.id.switcher2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editData.setHint(R.string.email);
                    Toast.makeText(EditContact.this, R.string.modeInputEmail, Toast.LENGTH_SHORT).show();
                } else if (isChecked == false) {
                    editData.setHint(R.string.phoneNumber);
                    Toast.makeText(EditContact.this, R.string.modeInputPhone, Toast.LENGTH_SHORT).show();
                }
                ifChecked = isChecked;
            }
        });

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSaved = new Intent(EditContact.this, MainActivity.class);
                name = editName.getText().toString();
                data = editData.getText().toString();
                db.contactDao().update(new Item(position, name, data));
                Toast.makeText(EditContact.this, R.string.editContact, Toast.LENGTH_SHORT).show();
                startActivity(intentSaved);
                finish();
            }
        });
    }
}
