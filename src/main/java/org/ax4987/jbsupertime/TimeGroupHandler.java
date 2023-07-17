package org.ax4987.jbsupertime;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class TimeGroupHandler extends JavaPlugin {
    public static JbSuperTime plugin;
    public static Map<String, Map<String,BukkitTask>> time_tasks = new HashMap<>();
    private static Map<String,Long> taskTime = new HashMap<>();
    private static Map<String,Long> taskDelay = new HashMap<>();
    private static Map<String,BukkitTask> tasks = new HashMap<>();
    private static Map<UUID, Long> killTimers = new HashMap<>();
    private static Map<String,Double> reallyCommand;
    TimeGroupHandler(JbSuperTime plugin){
        TimeGroupHandler.plugin = plugin;
    }
    public static void timeGroupCommandHandler(Player player, List<String> commands,long delay,String taskName){
        reallyCommand = RegularizationHandler.commandRegularizationHandler(commands);
        long startTime = System.currentTimeMillis();
        killTimers.put(player.getUniqueId(), startTime);
        tasks = time_tasks.get(player.getName());
        //init

        // 创建一个异步任务来检查计时器
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            // 检查计时器是否仍然存在
            if (killTimers.containsKey(player.getUniqueId())) {
                // 获取当前时间
                long currentTime = System.currentTimeMillis();
                // 计算时间差，单位为秒
                long timeElapsed = (currentTime - startTime) / 1000;
                // 如果时间差大于等于delay，则执行命令
                if (timeElapsed >= delay) {
                    if (player.isOnline()) {
                        // 玩家在线，执行命令
                        KillGroupHandler.executeCommandWithProbability(reallyCommand);
                    } else {
                        // 玩家不在线，等待玩家上线后执行命令
                        PlayerData playerData = new PlayerData(player.getName());
                        playerData.setCommands(reallyCommand);
                        PlayerData.DATA_MAP.put(player.getName(),playerData);
                    }
                }
                // 清除计时器
                killTimers.remove(player.getUniqueId());
            }
        }, delay * 20L); // 延迟60秒（20 ticks = 1 second）
        taskDelay.put(taskName,delay * 20L);
        taskTime.put(taskName,System.currentTimeMillis());
        tasks.put(taskName,task);
        time_tasks.put(player.getName(),tasks);
    }
    public static void addTimeGroup(String playerName, String group){
        Player player = getPlayer(playerName);
        List<String> commands = getCommands(group);
        int delay = JbSuperTime.time_group_delay.get(group);
        timeGroupCommandHandler(player,commands,delay,group);
    }
    public static void runTimeGroup(String playerName, String group){
        Player player = getPlayer(playerName);
        List<String> commands = getCommands(group);
        timeGroupCommandHandler(player,commands,0,group);
    }
    public static void addDelayTimerGroup(String playerName,String group,int delay){
        Player player = getPlayer(playerName);
        List<String> commands = getCommands(group);
        long currentDelay = currentDelay(group);
        long newDelay = currentDelay + delay;
        timeGroupCommandHandler(player,commands,newDelay,group);
    }
    private static Player getPlayer(String playerName){
        return Bukkit.getPlayer(playerName);
    }
    private static List<String> getCommands(String group){
        return JbSuperTime.time_group_commands.get(group);
    }
    public static long currentDelay(String taskName){
        long currentTime = System.currentTimeMillis();
        return taskDelay.get(taskName) - (currentTime - taskTime.get(taskName));
    }
}
