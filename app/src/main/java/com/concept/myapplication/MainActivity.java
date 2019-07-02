package com.concept.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> todo = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    private LinearLayout linear1;
    private ListView listview1;
    private LinearLayout linear3;
    private EditText edittext1;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        listview1 = (ListView) findViewById(R.id.listview1);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(!edittext1.getText().toString().isEmpty()){
                    todo.add(edittext1.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, todo);
        listview1.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
}
