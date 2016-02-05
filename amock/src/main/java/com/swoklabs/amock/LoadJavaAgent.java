package com.swoklabs.amock;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Steve on 2016-01-27.
 */
public class LoadJavaAgent {
    private static final String resourceName = "aspectjweaver-1.8.8.jar";
    private static final String tempDirBase = System.getProperty("java.io.tmpdir");
    private static final String tempDirSufix = "/agentfolder/";
    private static final String tempDirFullPath = tempDirBase + tempDirSufix;
    private static final String tempDirFullPathAndResource = tempDirBase + tempDirSufix + resourceName;

    public LoadJavaAgent() {
        final boolean isLoaded = isAspectJAgentLoaded();
        if (!isLoaded) {
            try {
                VirtualMachine vm = VirtualMachine.attach(getCurrentPID());
                copyAgentToTempFolder();
                vm.loadAgent(tempDirFullPathAndResource);
                vm.detach();
            } catch (AgentLoadException | AgentInitializationException | IOException | AttachNotSupportedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected static void copyAgentToTempFolder() throws Exception {
        createTempDirIfNotExist();
        if (!isAgentAlreadyThere()) {
            InputStream stream = null;
            OutputStream resStreamOut = null;
            try {
                File file = new File(tempDirFullPathAndResource);
                file.createNewFile();
                file.deleteOnExit();
                stream = LoadJavaAgent.class.getResourceAsStream(resourceName);
                if (stream == null) {
                    throw new Exception("Cannot find \"" + resourceName + "\".");
                }
                int readBytes;
                byte[] buffer = new byte[4096];
                resStreamOut = new FileOutputStream(tempDirFullPathAndResource);
                while ((readBytes = stream.read(buffer)) > 0) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                stream.close();
                resStreamOut.close();
            }
        }
    }

    protected static void createTempDirIfNotExist() {
        final File tempDir = new File(tempDirFullPath);
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
    }

    protected static boolean isAgentAlreadyThere() {
        final File checkIfJarExists = new File(tempDirFullPathAndResource);
        return checkIfJarExists.exists();
    }

    protected static boolean isAspectJAgentLoaded() {
        boolean isLoaded = true;
        try {
            Class.forName("org.aspectj.weaver.loadtime.Agent");
        } catch (ClassNotFoundException e) {
            isLoaded = false;
        }
        return isLoaded;
    }

    protected static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }
}
