package com.example.controllerapp.objects;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.controllerapp.activities.JoyStickActivity;
import com.example.controllerapp.activities.ListActivity;
import com.example.controllerapp.activities.MainMenu;
import com.google.common.base.Preconditions;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import geometry_msgs.Twist;

public class Publisher1 extends AbstractNodeMain {
    Publisher<geometry_msgs.Twist> publisher;
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android/slider");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher("cmd_vel", geometry_msgs.Twist._TYPE);
    }

    public void publish_method(int rotation) {
        geometry_msgs.Twist twist = publisher.newMessage();
        twist.getAngular().setZ(rotation/10);
        publisher.publish(twist);
    }
}