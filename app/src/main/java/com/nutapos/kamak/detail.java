package com.nutapos.kamak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.nutapos.kamak.helper.DbHelper;


public class detail extends AppCompatActivity {
    EditText txt_id, txt_name, txt_address,txtphone;
    Button btn_submit, btn_del;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_address = (EditText) findViewById(R.id.txt_address);
        txtphone = (EditText) findViewById(R.id.txt_phone);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_del = (Button) findViewById(R.id.btndel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);
        phone = getIntent().getStringExtra(MainActivity.TAG_PHONE);
        txt_id.setText(id);
        txt_name.setText(name);
        txt_address.setText(address);
        txtphone.setText(phone);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus();
            }
        });


    }
    private void hapus(){
        SQLite.delete(Integer.parseInt(id));
        blank();

    }
    private void blank() {
        Intent intent=new Intent(detail.this,MainActivity.class);
        startActivity(intent);
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txtphone.setText(null);
        txt_address.setText(null);
    }

    private void update() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_name.getText().toString().trim(),
                    txt_address.getText().toString().trim(),txtphone.getText().toString());
            blank();

            finish();
        }
    }
}
