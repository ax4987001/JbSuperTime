package org.ax4987.jbsupertime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JbCommandExecutor implements CommandExecutor {
    public static String playerName;
    public static String group;
    public static int time;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("jbsupertime")){
            if (args.length >= 3){
                if (args[0].equalsIgnoreCase("add") && args.length == 3){
                    playerName = args[1];
                    group = args[2];

                    return true;
                }else if (args[0].equalsIgnoreCase("run") && args.length == 3){
                    playerName = args[1];
                    group = args[2];

                    return true;
                }else if (args[0].equalsIgnoreCase("add-time") && args.length == 4){
                    playerName = args[1];
                    group = args[2];
                    time = Integer.parseInt(args[3]);

                    return true;
                }
            }else if (args[0].equalsIgnoreCase("reload") && args.length == 1){

                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
