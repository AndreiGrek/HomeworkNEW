package com.example.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditContact extends AppCompatActivity {

    private String name;
    private String phone;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        final EditText editName = findViewById(R.id.editName);
        final EditText editPhone = findViewById(R.id.editPhone);
        Intent intentPosition = getIntent();
        position = intentPosition.getIntExtra("POSITION", 0);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSaved = new Intent(EditContact.this, MainActivity.class);
                name = editName.getText().toString();
                phone = editPhone.getText().toString();
                intentSaved.putExtra("SAVEEDITNAME", name);
                intentSaved.putExtra("SAVEEDITNUMBER", phone);
                intentSaved.putExtra("POSITION", position);
                setResult(Activity.RESULT_OK, intentSaved);
                finish();
            }
        });
    }
}