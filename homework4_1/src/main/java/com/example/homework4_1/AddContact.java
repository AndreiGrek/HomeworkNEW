package com.example.homework4_1;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        final EditText editText1 = findViewById(R.id.addName);
        final EditText editText2 = findViewById(R.id.addPhone);

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSave = new Intent(AddContact.this, MainActivity.class);
                name = editText1.getText().toString();
                phone = editText2.getText().toString();
                intentSave.putExtra("SAVEADDNAME", name);
                intentSave.putExtra("SAVEADDNUMBER", phone);
                setResult(Activity.RESULT_OK, intentSave);
                finish();
            }
        });
    }
}