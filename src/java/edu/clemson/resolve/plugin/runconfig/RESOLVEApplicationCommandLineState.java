package edu.clemson.resolve.plugin.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.JavaCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.jar.JarApplicationCommandLineState;
import com.intellij.execution.jar.JarApplicationConfiguration;
import com.intellij.execution.runners.ExecutionEnvironment;

public class RESOLVEApplicationCommandLineState extends JavaCommandLineState {

    protected RESOLVEApplicationCommandLineState(
            ExecutionEnvironment environment) { super(environment); }

    @Override protected JavaParameters createJavaParameters()
            throws ExecutionException {
        final JavaParameters parameters = new JavaParameters();
        int i;
        i = 0;
        return null;
    }
}
