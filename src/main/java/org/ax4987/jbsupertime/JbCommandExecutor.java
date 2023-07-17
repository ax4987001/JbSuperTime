package org.ax4987.jbsupertime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JbCommandExecutor implements CommandExecutor {
    public static String playerName;
    public static String group;
    public static int time;
    private static final JbSuperTime plugin = JbSuperTime.INSTANCE;

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
                } else if (args[0].equalsIgnoreCase("add-time") && args.length == 4) {
                    playerName = args[1];
                    group = args[2];
                    time = Integer.parseInt(args[3]);
                    if (isGroup(group)){
                        if (isOnLine(playerName)){
                            if (hasTask(group,playerName)){
                                TimeGroupHandler.addDelayTimerGroup(playerName,group,time);
                            }
                        }
                    }
                    return true;
                }
            }else if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
                sender.sendMessage("正在重启插件...");
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
                    sender.sendMessage("插件重启完成！");
                }, 20L); // 延迟20个游戏刻执行重启逻辑

                return true;

            }
        }
        return false;
    }
    private boolean isGroup(String group){
        List<String> groups = JbSuperTime.timer_group_name;
        return groups.contains(group);
    }
    private boolean isOnLine(String playerName){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getName().equals(playerName)){
                return true;
            }
        }
        return false;
    }
    private boolean hasTask(String group,String playerName){
        if (TimeGroupHandler.time_tasks.containsKey(playerName)){
            return TimeGroupHandler.time_tasks.get(playerName).containsKey(group);
        }
        return false;
    }
}
