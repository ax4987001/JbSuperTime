package org.ax4987.jbsupertime;

import java.util.*;

public class RegularizationHandler {
    public static Map<String, Double> commandRegularizationHandler(List<String> commands){
        Random random = new Random();
        Map<String, Double> runningCommand = new HashMap<>();
        List<String> maybeRunning = new ArrayList<>();
        for (int i = 0;i < commands.size();i++){
            if (!isMandatory(commands.get(i))){
                maybeRunning.add(commands.get(i));
            }else {
                runningCommand.put(commands.get(i),getProbability(commands.get(i)));
            }
        }
        int run = random.nextInt(maybeRunning.size());
        runningCommand.put(maybeRunning.get(run),getProbability(maybeRunning.get(run)));
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
}
