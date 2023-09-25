package com.pokeapigo.core.common.extension;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestListener implements TestExecutionListener {

    public void testPlanExecutionStarted(TestPlan testPlan) {
        TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", "true");
        TestBaseConfiguration.postgreSQLContainer.start();
    }

    public void testPlanExecutionFinished(TestPlan testPlan) {
        TestBaseConfiguration.postgreSQLContainer.stop();
    }
}
