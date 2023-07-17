package org.ax4987.jbsupertime;

import java.util.*;

public class RegularizationHandler {
    public static Map<String, Double> commandRegularizationHandler(List<String> commands){
        Random random = new Random();
        Map<String, Double> runningCommand = new HashMap<>();
        List<String> maybeRunning = new ArrayList<>();
        for (String command : commands) {
            if (!isMandatory(command)) {
                maybeRunning.add(command);
            } else {
                runningCommand.put(getCommand(command), getProbability(command));
            }
        }
        int run = random.nextInt(maybeRunning.size());
        runningCommand.put(getCommand(maybeRunning.get(run)),getProbability(maybeRunning.get(run)));
        return runningCommand;
    }
    private static boolean isMandatory(String command){
        String[] parts = command.split("<->");
        return parts.length == 1;
    }
    private static double getProbability(String command){
        float percentage;
        String[] parts = command.split("<->");
        if (parts.length == 2){
            percentage = Float.parseFloat(parts[1]);
            return percentage;
        }
        return 100.0;
    }
    private static String getCommand(String command){
        String[] parts = command.split("<->");
        return parts[0];
    }
}
