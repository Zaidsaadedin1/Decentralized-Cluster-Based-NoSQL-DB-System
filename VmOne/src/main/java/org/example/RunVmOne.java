package org.example;


import org.example.BroadCasting.VmOneClientSocket;

import java.io.IOException;

public class RunVmOne {
    public static void main(String[] args) {
        // Create a thread for VmOne
        Thread vmOneThread = new Thread(() -> {
            try {
                VmOne vmOneInstance = VmOne.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        Thread VmOneAsClientThread = new Thread(() -> {
//            try {
//                VmOneClientSocket serversSocket = new VmOneClientSocket();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        VmOneAsClientThread.start();
//
        vmOneThread.start();

    }
}


