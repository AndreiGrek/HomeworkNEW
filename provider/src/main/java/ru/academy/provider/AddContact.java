package ru.academy.provider;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private AppDatabase db;
    private int counter;
    private SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        final EditText editText1 = findViewById(R.id.addName);
        final EditText editText2 = findViewById(R.id.addPhone);
        db = AppDatabase.getInstance(this);

        switch1=findViewById(R.id.switcher1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    editText2.setHint(R.string.email);
                    Toast.makeText(AddContact.this, R.string.modeInputEmail, Toast.LENGTH_SHORT).show();
                } else if (isChecked== false){
                    editText2.setHint(R.string.phoneNumber);
                    Toast.makeText(AddContact.this, R.string.modeInputPhone, Toast.LENGTH_SHORT).show();
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
                loadCounter();
                db.contactDao().insert(new Item(counter, name, phone));
                Toast.makeText(AddContact.this, R.string.addNewContact, Toast.LENGTH_SHORT).show();
                counter ++;
                saveCounter(counter);
                startActivity(intentSave);
                finish();
            }
        });
    }
    void saveCounter(int counter) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("COUNTER", counter);
        ed.apply();
    }

    public int loadCounter() {
        sPref = getPreferences(MODE_PRIVATE);
        this.counter = sPref.getInt("COUNTER", 0);
        return counter;
    }
}