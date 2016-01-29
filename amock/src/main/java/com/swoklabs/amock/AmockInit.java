package com.swoklabs.amock;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by Steve on 2016-01-27.
 */
public class AmockInit {
    public AmockInit() {

        try {
            System.out.println("Loading 123");
            VirtualMachine vm = VirtualMachine.attach(getCurrentPID());
            File file  = new File(ClassLoader.getSystemResource("aspectjweaver-1.8.8.jar").getFile());
            System.out.println("path 12 : "+file.getAbsolutePath());
            vm.loadAgent(file.getAbsolutePath());
            vm.detach();
            System.out.println("Detaching");
        } catch (AgentLoadException | AgentInitializationException | IOException | AttachNotSupportedException e) {
            e.printStackTrace();
        }

    }

    private static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }
}
