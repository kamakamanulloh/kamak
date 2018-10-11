package com.nutapos.kamak;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;

import com.nutapos.kamak.adapter.Adapter;
import com.nutapos.kamak.helper.DbHelper;
import com.nutapos.kamak.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtnm,txtno,txtalamat;
    Button btnsimpan;
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    public static final String TAG_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLite = new DbHelper(getApplicationContext());
        listView = (ListView) findViewById(R.id.list_view);
        Button btnrefresh=(Button)findViewById(R.id.btnrefresh);
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllData();
            }
        });
        btnsimpan=(Button)findViewById(R.id.btnsimpan);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,add.class);
                startActivity(intent);
            }
        });
        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final String idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();
                final String phone_ = itemList.get(position).getPhone();
                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this, detail.class);

                intent.putExtra(TAG_ID, idx);
                intent.putExtra(TAG_NAME, name);
                intent.putExtra(TAG_ADDRESS, address);
                intent.putExtra(TAG_PHONE, phone_);

                startActivity(intent);
            }
        });

    }
    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String nm = row.get(i).get(TAG_NAME);
            String addr = row.get(i).get(TAG_ADDRESS);
            String call = row.get(i).get(TAG_PHONE);

            Data data = new Data();

            data.setId(id);
            data.setName(nm);
            data.setAddress(addr);
            data.setPhone(call);

            itemList.add(data);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }

}
