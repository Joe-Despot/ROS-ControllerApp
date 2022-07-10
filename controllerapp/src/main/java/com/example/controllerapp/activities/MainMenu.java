package com.example.controllerapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.controllerapp.R;


public class MainMenu extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    private ImageView imageAdd;
    private ImageView imageAddPlus;
    private ImageView imageList;
    private ImageView imageController;
    private ImageView imageCamera;
    private ImageView imagePlusHybrid;
    private ImageView imageControllerHybrid;
    private ImageView imageCameraHybrid;
    private ImageView imageMisc;
    private ImageView sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageAdd = findViewById(R.id.imageViewAdd);
        imageAddPlus = findViewById(R.id.imageViewAddPlus);
        imageList = findViewById(R.id.imageViewList);
        imageController = findViewById(R.id.imageViewController);
        imageCamera = findViewById(R.id.imageViewCamera);
        imagePlusHybrid = findViewById(R.id.imageViewHybrid1);
        imageControllerHybrid = findViewById(R.id.imageViewHybrid3);
        imageCameraHybrid = findViewById(R.id.imageViewHybrid2);
        imageMisc = findViewById(R.id.imageViewHybrid);
        sideBar = findViewById(R.id.mm_sidebar);

        sideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), SidebarActivity.class);
                startActivity(intent);
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        imageAddPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        imageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        imageController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), JoyStickActivity.class);
                startActivity(intent);
            }
        });

        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        imageCameraHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), HybridActivity.class);
                startActivity(intent);
            }
        });

        imageControllerHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), HybridActivity.class);
                startActivity(intent);
            }
        });

        imageCameraHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), HybridActivity.class);
                startActivity(intent);
            }
        });

        imageMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation));
                Intent intent = new Intent(getApplicationContext(), FunctionsActivity.class);
                startActivity(intent);
            }
        });




    }
}