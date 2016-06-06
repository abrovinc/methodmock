package com.swoklabs.methodmock;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Steve on 2016-01-27.
 */
public class LoadJavaAgent {
    private static final String resourceName = "/aspectjweaver.jar";
    private static final String tempDirBase = System.getProperty("java.io.tmpdir");
    private static final String tempDirSufix = "/agentfolder";
    private static final String tempDirFullPath = tempDirBase + tempDirSufix;
    private static final String tempDirFullPathAndResource = tempDirBase + tempDirSufix + resourceName;
    private Logger logger = Logger.getLogger(LoadJavaAgent.class);

    public LoadJavaAgent() {
        logger.info("IsLoaded . " + isAspectJAgentLoaded());
        while (!isAspectJAgentLoaded()) {
            logger.info("Trying to load aspectj-weaver");
            try {
                VirtualMachine vm = VirtualMachine.attach(getCurrentPID());
                copyAgentToTempFolder();
                vm.loadAgent(tempDirFullPathAndResource);
                vm.detach();
            } catch (IOException e) {
                logger.info("IOException when trying to load aspectj-weaver");
                throw new RuntimeException(e.getMessage(), e.getCause());
            } catch (AgentInitializationException e) {
                logger.info("AgentInitializationException when trying to load aspectj-weaver");
                throw new RuntimeException(e.getMessage(), e.getCause());
            } catch (AttachNotSupportedException e) {
                logger.info("AttachNotSupportedException when trying to load aspectj-weaver");
                throw new RuntimeException(e.getMessage(), e.getCause());
            } catch (AgentLoadException e) {
                logger.info("AgentLoadException when trying to load aspectj-weaver");
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

    protected static void copyAgentToTempFolder() throws IOException {
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
                    throw new FileNotFoundException("Cannot find \"" + resourceName + "\".");
                }
                int readBytes;
                byte[] buffer = new byte[4096];
                resStreamOut = new FileOutputStream(tempDirFullPathAndResource);
                while ((readBytes = stream.read(buffer)) > 0) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            org.aspectj.weaver.loadtime.Agent.getInstrumentation();
        } catch (NoClassDefFoundError e) {
            isLoaded = false;
        } catch (UnsupportedOperationException e) {
            isLoaded = false;
        }
        return isLoaded;
    }

    protected static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }
}
