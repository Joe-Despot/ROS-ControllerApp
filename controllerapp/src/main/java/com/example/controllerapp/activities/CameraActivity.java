package com.example.controllerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.controllerapp.R;
import com.github.rosjava.android_remocons.common_tools.apps.RosAppActivity;

import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.RosActivity;
import org.ros.android.view.RosImageView;
import org.ros.android.view.VirtualJoystickView;
import org.ros.namespace.NameResolver;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.io.IOException;
import java.net.URI;

import sensor_msgs.CompressedImage;


public class CameraActivity extends RosActivity {

    private RosImageView<sensor_msgs.CompressedImage> cameraView;
    private URI uri;

    public CameraActivity() {
            super("ControllerApp", "ControllerApp", URI.create(""));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_camera);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String uri_sent = intent.getStringExtra("send_ip");
        try {
            uri = URI.create(ListActivity.getIP());
        }catch(NullPointerException e){
            Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }

        cameraView = (RosImageView<CompressedImage>) findViewById(R.id.image);
        cameraView.setMessageType(sensor_msgs.CompressedImage._TYPE);
        cameraView.setMessageToBitmapCallable(new BitmapFromCompressedImage());

    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {

        try {
            java.net.Socket socket = new java.net.Socket(uri.getHost(), uri.getPort());
            java.net.InetAddress local_network_address = socket.getLocalAddress();
            socket.close();

            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), uri);
            cameraView.setTopicName("camera/rgb/image_raw/compressed");
            nodeMainExecutor.execute(cameraView, nodeConfiguration
                    .setNodeName("android/camera_view"));

        } catch (NullPointerException | WindowManager.BadTokenException |IOException e) {
            Intent intent2 = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent2);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,"Kill");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case 0:
                onDestroy();
                break;
        }
        return true;
    }
}
