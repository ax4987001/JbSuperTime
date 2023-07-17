package org.ax4987.jbsupertime;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TimerGroupHandler extends JavaPlugin {
    public static JbSuperTime plugin;
    public static String formattedTime;
    public static String timer_group = null;
    public static List<String> timer_groups = new ArrayList<>();
    public static List<String> timers = new ArrayList<>();
    public static List<String> before_timer = new ArrayList<>();
    public static Map<String,Double> timer_group_commands;
    public static LocalTime currentTime;
    public static Collection<? extends Player> players;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
    TimerGroupHandler(JbSuperTime plugin){
        TimeGroupHandler.plugin = plugin;
    }
    @Override
    public void onEnable() {

        // 在服务器启动时注册任务，每隔一秒执行一次
        getServer().getScheduler().runTaskTimer(plugin, () -> {
            players = Bukkit.getServer().getOnlinePlayers();
            // 获取当前的时间
            currentTime = LocalTime.now();
            // 定义时间格式，并将连接符设置为"-"
            // 格式化时间
            formattedTime = currentTime.format(formatter);

            for (Map.Entry<String, String> entry : JbSuperTime.timer_group_timer.entrySet()) {
                if (entry.getValue().equals(formattedTime)) {
                    timer_group = entry.getKey();
                    break;
                }
            }
            //寻找对应的组
            if (timer_group != null){
                for (Player player : players){
                    timer_group_commands =  RegularizationHandler.commandRegularizationHandler(JbSuperTime.timer_group_commands.get(timer_group));
                    KillGroupHandler.executeCommandWithProbability(timer_group_commands);

                }
            }else {
                timers = new ArrayList<>(JbSuperTime.timer_group_timer.values());
                LocalDateTime timer;
                LocalDateTime now = LocalDateTime.parse(formattedTime, formatter);
                for (String s : timers){
                    timer = LocalDateTime.parse(s, formatter);
                    if (timer.isBefore(now)){
                        before_timer.add(timer.format(formatter));
                    }
                }
                if (!before_timer.isEmpty()) {
                    for (String s : before_timer) {
                        for (Map.Entry<String, String> entry : JbSuperTime.timer_group_timer.entrySet()) {
                            if (entry.getValue().equals(s)) {
                                timer_groups.add(entry.getKey());
                            }
                        }
                    }
                    if (!timer_groups.isEmpty()) {
                        for (String s : timer_groups) {
                            for (Player player : players){
                                timer_group_commands = RegularizationHandler.commandRegularizationHandler(JbSuperTime.timer_group_commands.get(s));
                                KillGroupHandler.executeCommandWithProbability(timer_group_commands);
                            }

                        }
                    }
                }

            }



        }, 0L, 20L); // 在0刻开始执行，每隔20刻执行一次（即每秒执行一次）
    }
    @Override
    public void onDisable() {
        // 在服务器关闭时取消任务
        getServer().getScheduler().cancelTasks(plugin);
    }
}

