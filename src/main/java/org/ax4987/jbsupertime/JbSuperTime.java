package org.ax4987.jbsupertime;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JbSuperTime extends JavaPlugin {
    public static JbSuperTime INSTANCE;
    public static FileConfiguration config;
    public static ConfigurationSection kill_group;
    public static List<String> kill_group_name = new ArrayList<>();
    public static Map<String,List<String>> kill_group_mythic_mob_id = new HashMap<>();
    public static Map<String,List<String>> kill_group_commands = new HashMap<>();
    public static Map<String,Integer> kill_group_mm_time = new HashMap<>();
    public static Map<String,List<String>> kill_group_time_group_name = new HashMap<>();
    public static boolean kill_group_is_read = false;
    //Kill_Group
    public static ConfigurationSection time_group;
    public static List<String> time_group_name = new ArrayList<>();
    public static Map<String,List<String>> time_group_commands = new HashMap<>();
    public static Map<String,Integer> time_group_delay = new HashMap<>();
    public static boolean time_group_is_read = false;
    //Time_Group
    public static ConfigurationSection timer_group;
    public static List<String> timer_group_name = new ArrayList<>();
    public static Map<String,String> timer_group_timer = new HashMap<>();
    public static Map<String,List<String>> timer_group_commands = new HashMap<>();
    public static boolean timer_group_is_read = false;
    //Timer_Group
    public static ConfigurationSection timer_task;
    public static List<String> timer_task_name = new ArrayList<>();
    public static Map<String,String> timer_task_timer = new HashMap<>();
    public static Map<String,List<String>> timer_task_commands = new HashMap<>();
    public static boolean timer_task_is_read = false;
    //Timer_Task
    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        saveDefaultConfig();
        config = getConfig();
        String groups;
        if (config.getConfigurationSection("kill-group") != null){
            kill_group_is_read = true;
            kill_group = config.getConfigurationSection("kill-group");
            kill_group_name = config.getStringList("kill-group");
            for (String s : kill_group_name) {
                groups = s;
                kill_group_mythic_mob_id.put(groups, kill_group.getStringList(groups + ".mythic-mob-id"));
                kill_group_commands.put(groups, kill_group.getStringList(groups + ".commands"));
                kill_group_mm_time.put(groups, kill_group.getInt(groups + ".mm-time"));
                kill_group_time_group_name.put(groups, kill_group.getStringList(groups + ".time-group"));
            }
            //Kill_Group
        }

        if (config.getConfigurationSection("time-group") != null){
            time_group_is_read = true;
            time_group = config.getConfigurationSection("time-group");
            time_group_name = config.getStringList("time-group");
            for (String s : time_group_name) {
                groups = s;
                time_group_commands.put(groups, time_group.getStringList(groups + ".commands"));
                time_group_delay.put(groups, time_group.getInt(groups + ".delay"));
            }
            //Time_Group
        }

        if (config.getConfigurationSection("timer-group") != null){
            timer_group_is_read = true;
            timer_group = config.getConfigurationSection("timer-group");
            timer_group_name = config.getStringList("timer-group");
            for (String s : timer_group_name) {
                groups = s;
                timer_group_timer.put(groups, timer_group.getString(groups + ".timer"));
                timer_group_commands.put(groups, timer_group.getStringList(groups + ".commands"));
            }
            //Timer_Group
        }

        if (config.getConfigurationSection("timer-task") != null){
            timer_task_is_read = true;
            timer_task = config.getConfigurationSection("timer-task");
            timer_task_name = config.getStringList("timer-task");
            for (String s : timer_task_name) {
                groups = s;
                timer_task_timer.put(groups, timer_task.getString(groups + ".timer"));
                timer_task_commands.put(groups, timer_task.getStringList(groups + ".commands"));
            }
            //Timer_Task
        }


        getCommand("jbsupertime").setExecutor(new JbCommandExecutor());

        if (kill_group_is_read){
            getServer().getPluginManager().registerEvents(new KillGroupHandler(), this);
            SendMessage("kill-group running\n");
        }else {
            SendMessage("kill-group not found\n");
        }
        if (time_group_is_read){
            SendMessage("time-group running\n");
            new TimeGroupHandler(this);
        }else {
            SendMessage("time-group not found\n");
        }
        if (timer_group_is_read){
            new TimerGroupHandler(this);
            SendMessage("timer-group running\n");
        }else {
            SendMessage("timer-group not found\n");
        }
        if (timer_task_is_read){
            new TimerTaskHandler(this);
            SendMessage("timer-task running\n");
        }else {
            SendMessage("timer-task not found\n");
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MyPlaceholderExpansion().register();
            getLogger().info("已注册PAPI变量");
        } else {
            getLogger().warning("未找到PlaceholderAPI插件");
        }

        SendMessage("\n-------JbSuperTime Running-------\n");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void SendMessage(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
