package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.jar.JarApplicationCommandLineState;
import com.intellij.execution.jar.JarApplicationConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;

public class RESOLVEApplicationCommandLineState extends JarApplicationCommandLineState {

    //Todo: check out JarApplicationConfiguration
    public RESOLVEApplicationCommandLineState(
            JarApplicationConfiguration configuration,
            ExecutionEnvironment environment) {
        super(configuration, environment);
    }
}
