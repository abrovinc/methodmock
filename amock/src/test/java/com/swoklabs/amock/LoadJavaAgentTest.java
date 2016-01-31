package com.swoklabs.amock;

import org.junit.Test;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class LoadJavaAgentTest extends LoadJavaAgent {

    @Test
    public void copyAgentToTempFolderTest(){

    }

    @Test
    public void isAgentAlreadyThereTest(){
        assertEquals(true, isAgentAlreadyThere());
    }

    @Test
    public void isAspectJAgentLoadedTest(){
        assertEquals(true, isAspectJAgentLoaded());
    }

    @Test
    public void getCurrentPIDTest(){
        final String currentPid = getCurrentPID();
        final String jvm = ManagementFactory.getRuntimeMXBean().getName();
        final String currentRuntimePid = jvm.substring(0, jvm.indexOf('@'));
        assertEquals(currentRuntimePid,currentPid);
    }
}
