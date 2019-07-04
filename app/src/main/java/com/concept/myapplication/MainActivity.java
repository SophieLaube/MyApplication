package com.concept.myapplication;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText textToType;
    private ArrayList<String> todo = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listview2;
    String selectedItem;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadData();
        initialize();
        listviewInitializeLogic();
    }

    private void initialize(){
        textToType = findViewById(R.id.textToType);
        listview2 = (ListView) findViewById(R.id.listview2);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    // Initialize the listview when called
    private void listviewInitializeLogic(){
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, todo);
//        View emptyView = getLayoutInflater().inflate(R.layout.empty_view,null);
//        addContentView(emptyView,listview2.getLayoutParams());
        //You're using the same params as lv
        listview2.setEmptyView(findViewById(R.id.empty_text));
        listview2.setAdapter(arrayAdapter);
        listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                //Popup a Snackbar with a delete button on long click
                Snackbar snackbar = Snackbar.make(coordinatorLayout,
                        R.string.delete_selected_item,Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.delete,new MyClick());
                snackbar.show();
                return true;
            }
        });
        arrayAdapter.notifyDataSetChanged();
    }

    public void openDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.main_dialog);
        dialog.setTitle(R.string.add_todo);
        textToType = dialog.findViewById(R.id.textToType);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textToType.getText().toString().isEmpty()){
                    todo.add(textToType.getText().toString());
                    textToType.setText("");
                    dialog.dismiss();
                    saveData();
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        dialog.show();
    }
    //Just a function that creates a long toast on the screen when called
    private void showToast(String _s){
        Toast.makeText(getApplicationContext(),_s,Toast.LENGTH_LONG).show();
    }
    //Saves the listview added items to memory via Gson
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(todo);
        editor.putString("tasks", json);
        editor.apply();
    }
    public class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Code to delete selected element
            arrayAdapter.remove(selectedItem);
            saveData();
            arrayAdapter.notifyDataSetChanged();
        }
    }
    //Reads previously added items to the listview from the memory via Gson
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("tasks", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        todo = gson.fromJson(json, type);

        if (todo == null) {
            todo = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.clearAll:
                arrayAdapter.clear();
                arrayAdapter.notifyDataSetChanged();
                saveData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
