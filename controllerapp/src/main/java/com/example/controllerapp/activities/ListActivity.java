package com.example.controllerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.controllerapp.R;
import com.example.controllerapp.adapters.ListViewAdapter;
import com.example.controllerapp.objects.Model;
import com.example.controllerapp.utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static String message_ip;
    private String reciever_ip;
    private String reciever_port;
    private String name;
    private String[] names;
    private String[] adresses;
    private int icon;
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<Model> arrayList = new ArrayList<Model>();
    private ImageView button_save;
    private ImageView button_clear;
    private ImageView button_add;
    Model model;
    private ImageView arrowBack;
    private int delete_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.showToast(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        loadData();
        init();

        model = (Model) getIntent().getSerializableExtra("Added Model");
        if(model != null)
            arrayList.add(model);

        listView = findViewById(R.id.listview);

        if(arrayList==null)
            arrayList.add(new Model("null", "null"));
        adapter = new ListViewAdapter(this, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
               // Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                message_ip = arrayList.get(position).getFull_ip();
               // intent.putExtra("send_ip", message_ip);
                //startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                delete_position = position;
                PopupMenu p = new PopupMenu(ListActivity.this, view);
                p.getMenuInflater().inflate(R.menu.main_popup_menu, p.getMenu());
                p.show();
                return true;
            }
        });

    }

    private void init() {
        arrowBack = findViewById(R.id.arrow_back_hybrid);
        button_clear = findViewById(R.id.imageViewClaerAll);
        button_save = findViewById(R.id.imageViewSave);
        button_add = findViewById(R.id.imageViewAddNew);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                clearData();
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                saveData();
            }
        });
    }

    private void clearData(){
        arrayList.clear();
        saveData();
        adapter.notifyDataSetChanged();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefrences" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("list_vehicles", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefrences" , MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list_vehicles", null);
        Type type = new TypeToken<ArrayList<Model>>(){}.getType();
        arrayList = gson.fromJson(json, type);

        if(arrayList == null){
            arrayList = new ArrayList<Model>();
            arrayList.add(new Model("null", "null"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s){
                if(TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }else{
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id== R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(MenuItem view) {
        arrayList.remove(delete_position);
        saveData();
        adapter.notifyDataSetChanged();
    }

    public static String getIP(){
        return message_ip;
    }



}