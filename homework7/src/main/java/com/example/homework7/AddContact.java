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

public class AddContact extends AppCompatActivity {
    private String name;
    private String phone;
    private Switch switch1;
    private boolean ifChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        final EditText editText1 = findViewById(R.id.addName);
        final EditText editText2 = findViewById(R.id.addPhone);

        switch1=findViewById(R.id.switcher1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editText2.setHint("E-mail");
                    Toast.makeText(AddContact.this, "Режим ввода E-mail", Toast.LENGTH_SHORT).show();
                } else if (isChecked== false){
                    editText2.setHint("+375(29) XXX-XX-XX");
                    Toast.makeText(AddContact.this, "Режим ввода телефона", Toast.LENGTH_SHORT).show();
                }
                ifChecked = isChecked;
            }
        });


        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSave = new Intent(AddContact.this, MainActivity.class);
                name = editText1.getText().toString();
                phone = editText2.getText().toString();
                intentSave.putExtra("SAVEADDNAME", name);
                intentSave.putExtra("SAVEADDNUMBER", phone);
                intentSave.putExtra("ISCHEKED", ifChecked);
                setResult(Activity.RESULT_OK, intentSave);
                finish();
            }
        });
    }
}