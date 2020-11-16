package com.example.homework7;

import android.app.Activity;
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
        switch2=findViewById(R.id.switcher2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editPhone.setHint("E-mail");
                    Toast.makeText(EditContact.this, "Режим ввода E-mail", Toast.LENGTH_SHORT).show();
                } else if (isChecked== false){
                    editPhone.setHint("+375(29) XXX-XX-XX");
                    Toast.makeText(EditContact.this, "Режим ввода телефона", Toast.LENGTH_SHORT).show();
                }
                ifChecked = isChecked;
            }
        });


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
