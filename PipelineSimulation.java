package com.revature.lab;

import java.io.File;
import java.io.IOException;

public class PipelineSimulation {

    public static void main(String[] args) {
        System.out.println("--- Starting Pipeline ---");

        // Stage 1: Build
        if (!runStage("Build", () -> checkSourceCode()))
            return;

        // Stage 2: Test
        if (!runStage("Test", () -> runRandomTests()))
            return;

        // Stage 3: Deploy
        if (!runStage("Deploy", () -> deployArtifact()))
            return;

        System.out.println("--- Pipeline SUCCESS ---");
    }

    private static boolean runStage(String name, Supplier<Boolean> task) {
        System.out.println("Running Stage: " + name + "...");

        boolean result = task.get();
        if (!result) {
            System.out.println("Stage " + name + " failed.");
            return false;
        }
        return true;
    }

    private static boolean checkSourceCode() {
        File file = new File("source_code.txt");
        return file.exists();
    }

    private static boolean runRandomTests() {
        return Math.random() > 0.5;
    }

    private static boolean deployArtifact() {
        File file = new File("artifact.jar");
        // move file to deploy folder if it exists
        if (file.exists()) {
            File deployFolder = new File("deploy");
            if (!deployFolder.exists()) {
                deployFolder.mkdir();
            } else {
                return false;
            }
            try {
                file.renameTo(new File("deploy/artifact.jar"));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    // TODO: Implement helper methods

    interface Supplier<T> {
        T get();
    }
}
