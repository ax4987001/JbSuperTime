package org.ax4987.jbsupertime;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

public class TimerTaskHandler extends JbSuperTime {
    public static Collection<? extends Player> players;
    public static LocalTime currentTime;
    public static String formattedTime;
    public static String timer_task = null;
    public static Map<String,Double> timer_task_commands;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH-mm-ss");

    @Override
    public void onEnable() {
        // 在服务器启动时注册任务，每隔一秒执行一次
        getServer().getScheduler().runTaskTimer(new JbSuperTime(), () -> {
            players = Bukkit.getServer().getOnlinePlayers();
            // 获取当前的时间
            currentTime = LocalTime.now();
            // 定义时间格式，并将连接符设置为"-"
            // 格式化时间
            formattedTime = currentTime.format(formatter);

            for (Map.Entry<String, String> entry : JbSuperTime.timer_task_timer.entrySet()) {
                if (entry.getValue().equals(formattedTime)) {
                    timer_task = entry.getKey();
                    break;
                }
            }
            //寻找对应的组
            if (timer_task != null){
                for (Player player : players){
                    timer_task_commands =  RegularizationHandler.commandRegularizationHandler(JbSuperTime.timer_task_commands.get(timer_task));
                    KillGroupHandler.executeCommandWithProbability(timer_task_commands);
                }
            }
        }, 0L, 20L); // 在0刻开始执行，每隔20刻执行一次（即每秒执行一次）
    }
    @Override
    public void onDisable() {
        // 在服务器关闭时取消任务
        getServer().getScheduler().cancelTasks(new JbSuperTime());
    }
}
