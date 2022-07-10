package com.example.controllerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.controllerapp.R;
import com.example.controllerapp.objects.Model;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText ip_adress_txt;
    private EditText port_adress_txt;
    private EditText name_txt;
    private Button main_enter_btn;
    private Button goToListBtn;
    private String ip_adress;
    private String port_adress;
    private String name;
    private Model model;
    private ImageView arrowBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle_activity);
        init();
    }

    protected void init() {
        main_enter_btn = findViewById(R.id.button_enter);
        goToListBtn = findViewById(R.id.buttonGotolist);
        ip_adress_txt = findViewById(R.id.ip_adress);
        port_adress_txt = findViewById(R.id.port_id);
        name_txt = findViewById(R.id.name);
        arrowBack = findViewById(R.id.arrow_back_hybrid);


        main_enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                ip_adress = ip_adress_txt.getText().toString();
                port_adress = port_adress_txt.getText().toString();
                name = name_txt.getText().toString();
                String full_ip = "http://" + ip_adress + ":" + port_adress;
                model = new Model(name, full_ip);
                Intent intent = new Intent(AddVehicleActivity.this, ListActivity.class);
                intent.putExtra("Added Model", model);
                startActivity(intent);
            }
        });
        goToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(AddVehicleActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(AddVehicleActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }

    public String getIp(){
        return ip_adress;
    }
    public String getPort(){
        return port_adress;
    }
}