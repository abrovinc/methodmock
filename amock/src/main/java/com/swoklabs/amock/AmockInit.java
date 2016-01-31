package com.swoklabs.amock;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;

/**
 * Created by Steve on 2016-01-27.
 */
public class AmockInit {
    public AmockInit() {

        try {
            System.out.println("Loading 123");
            VirtualMachine vm = VirtualMachine.attach(getCurrentPID());
            final String totalPath = copyAgentToTempFolder();
            System.out.println("total Path : "+totalPath);
            vm.loadAgent(totalPath);
            vm.detach();
            System.out.println("Detaching");
        } catch (AgentLoadException | AgentInitializationException | IOException | AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    static public String copyAgentToTempFolder() throws Exception {
        String resourceName = "/aspectjweaver-1.8.8.jar";
        final String tempDirectoryPath = Files.createTempDirectory("agent").toString();
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = AmockInit.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(tempDirectoryPath + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return tempDirectoryPath + resourceName;
    }

    private static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }
}
