package com.example.controllerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.example.controllerapp.R;
import com.example.controllerapp.objects.Publisher1;
import com.example.controllerapp.utils.Util;
import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.view.RosImageView;
import org.ros.android.view.RosTextView;
import org.ros.android.view.VirtualJoystickView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import geometry_msgs.Twist;
import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import sensor_msgs.CompressedImage;

public class HybridActivity extends RosActivity  {
    private VirtualJoystickView virtualJoystickView;
    private URI uri;
    private ImageView arrowBack;
    private SeekBar seekBar;
    private Publisher1 talker_slider;
    private static final java.lang.String odomTopic = "/odom";
    List<Float> arrayX = new ArrayList<Float>();
    List<Float> arrayY = new ArrayList<Float>();
    private RosTextView<Twist> rosTextView,textTheta;
    private RosTextView<nav_msgs.Odometry> linex, liney;
    int counter;
    private RosImageView<sensor_msgs.CompressedImage> cameraView;


    public HybridActivity() {
        super("ControllerApp", "ControllerApp",URI.create(""));
    }

   // protected HybridActivity(String notificationTicker, String notificationTitle, URI customMasterUri) {
    //    super(notificationTicker, notificationTitle, customMasterUri);
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);

        Intent intent = getIntent();
        try {
            uri = URI.create(ListActivity.getIP());
        }catch(NullPointerException e){
            Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent1);
        }

        rosTextView = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextx);
        textTheta = (RosTextView<geometry_msgs.Twist>) findViewById(R.id.rostextTheta);
        linex = (RosTextView<nav_msgs.Odometry>) findViewById(R.id.rostextPX);
        liney = (RosTextView<nav_msgs.Odometry>) findViewById(R.id.rostextPY);
        virtualJoystickView = (VirtualJoystickView) findViewById(R.id.virtual_joystick);
        cameraView = (RosImageView<CompressedImage>) findViewById(R.id.image);
        arrowBack = findViewById(R.id.arrow_back_hybrid);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(30);

        cameraView.setMessageType(sensor_msgs.CompressedImage._TYPE);
        cameraView.setMessageToBitmapCallable(new BitmapFromCompressedImage());

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init(final NodeMainExecutor nodeMainExecutor){
        try {
            java.net.Socket socket = new java.net.Socket(uri.getHost(), uri.getPort());
            java.net.InetAddress local_network_address = null;
            try {
                local_network_address = socket.getLocalAddress();
            }catch(NullPointerException e){
                Intent intent1 = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent1);
            }
            socket.close();

            final NodeConfiguration nodeConfiguration1 =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), uri);
            talker_slider = new Publisher1();
            nodeMainExecutor.execute(talker_slider, nodeConfiguration1);

            NodeConfiguration nodeConfiguration =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), uri);
            virtualJoystickView.setTopicName("cmd_vel");

            NodeConfiguration nodeConfiguration_cam =
                    NodeConfiguration.newPublic(local_network_address.getHostAddress(), uri);
            cameraView.setTopicName("camera/rgb/image_raw/compressed");
            nodeMainExecutor.execute(cameraView, nodeConfiguration_cam
                    .setNodeName("android/camera_view"));

            nodeMainExecutor.execute(virtualJoystickView,
                    nodeConfiguration.setNodeName("android/virtual_joystick"));
            nodeConfiguration = NodeConfiguration.newPublic(InetAddressFactory
                    .newNonLoopback().getHostAddress(), uri);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    talker_slider.publish_method(progress-30);
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekBar.setProgress(30);
                    talker_slider.publish_method(0);
                }
            });

            linex.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    counter+=1;
                    if (counter%5==0) {
                        update();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            liney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    counter+=1;
                    if (counter%5==0) {
                        update();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            rosTextView.setTopicName("cmd_vel");
            textTheta.setTopicName("cmd_vel");
            linex.setTopicName(odomTopic.toString());
            liney.setTopicName(odomTopic.toString());

            rosTextView.setMessageType(geometry_msgs.Twist._TYPE);
            textTheta.setMessageType(geometry_msgs.Twist._TYPE);
            linex.setMessageType(nav_msgs.Odometry._TYPE);
            liney.setMessageType(nav_msgs.Odometry._TYPE);

            nodeMainExecutor.execute(rosTextView, nodeConfiguration.setNodeName("android/textx"));
            nodeMainExecutor.execute(textTheta, nodeConfiguration.setNodeName("android/textT"));
            nodeMainExecutor.execute(linex, nodeConfiguration.setNodeName("android/posX"));
            nodeMainExecutor.execute(liney, nodeConfiguration.setNodeName("android/posY"));

            rosTextView.setMessageToStringCallable(new MessageCallable<String, Twist>() {
                @Override
                public java.lang.String call(Twist message) {
                    double messageV = message.getLinear().getX();
                    NumberFormat form = new DecimalFormat("#0.00000");
                    return java.lang.String.valueOf(form.format(messageV));
                }
            });

            textTheta.setMessageToStringCallable(new MessageCallable<java.lang.String, Twist>() {
                @Override
                public java.lang.String call(Twist message) {
                    double messageV = message.getAngular().getZ();
                    NumberFormat form = new DecimalFormat("#0.00000");
                    return java.lang.String.valueOf(form.format(messageV));
                }
            });

            linex.setMessageToStringCallable(new MessageCallable<java.lang.String, nav_msgs.Odometry>() {
                @Override
                public java.lang.String call(nav_msgs.Odometry message) {
                    double messageV = message.getPose().getPose().getPosition().getX();
                    NumberFormat form = new DecimalFormat("#0.00000");
                    return form.format(messageV);
                }
            });



            liney.setMessageToStringCallable(new MessageCallable<java.lang.String, nav_msgs.Odometry>() {
                @Override
                public java.lang.String call(nav_msgs.Odometry message) {
                    double messageV = message.getPose().getPose().getPosition().getY();
                    NumberFormat form = new DecimalFormat("#0.00000");
                    return form.format(messageV);
                }
            });


        } catch (NullPointerException | IOException e) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }
    }

    private void update() {
        double number = Double.parseDouble(linex.getText().toString()) * (100.0);
        double number2 = Double.parseDouble(liney.getText().toString()) * (100.0);
        Float numberf = (float) number;
        Float number2f = (float) number2;
        arrayX.add(numberf);
        arrayY.add(number2f);
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