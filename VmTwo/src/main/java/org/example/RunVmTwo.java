package org.example;

import org.example.BroadCasting.VmTwoClientSocket;

import java.io.IOException;

public class RunVmTwo {
    public static void main(String[] args) {
        // Create a thread for VmOne
        Thread vmTwoThread = new Thread(() -> {
            try {
                VmTwo vmTwoInstance = VmTwo.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        Thread VmTwoAsClientThread = new Thread(() -> {
//            try {
//                VmTwoClientSocket serversSocket = new VmTwoClientSocket();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        // Start both threads
//        VmTwoAsClientThread.start();
//
        vmTwoThread.start();

    }
}
