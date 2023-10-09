package org.example;
import org.example.BroadCasting.ServersSocket;

import java.io.IOException;

public class RunAffinityVm {
    public static void main(String[] args) {
        // Create a thread for VmOne
        Thread AffinityThread = new Thread(() -> {
            try {
                AffinityVm affinityVmInstance = AffinityVm.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        Thread newThread = new Thread(() -> {
//            try {
//                ServersSocket serversSocket = ServersSocket.getInstance();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        newThread.start();
        AffinityThread.start();
    }
}
