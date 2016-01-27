package com.swoklabs.amock;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by Steve on 2016-01-27.
 */
public class AmockInit {
    public AmockInit(){

        try {

            VirtualMachine vm = VirtualMachine.attach(getCurrentPID());
            vm.loadAgent(this.getClass().getClassLoader().getResource("/aspectjweaver-1.8.8.jar").getPath().toString());
            vm.detach();
        } catch (AgentLoadException | AgentInitializationException | IOException | AttachNotSupportedException e) {
            e.printStackTrace();
        }

    }

    public static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }
}
