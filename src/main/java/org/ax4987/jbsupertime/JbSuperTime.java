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
    public static FileConfiguration config;
    public static ConfigurationSection kill_group;
    public static List<String> kill_group_name = new ArrayList<>();
    public static Map<String,List<String>> kill_group_mythic_mob_id = new HashMap<>();
    public static Map<String,List<String>> kill_group_commands = new HashMap<>();
    public static int kill_group_mm_time = 0;
    public static Map<String,List<String>> kill_group_time_group_name = new HashMap<>();
    //Kill_Group
    public static ConfigurationSection time_group;
    public static List<String> time_group_name = new ArrayList<>();
    public static Map<String,List<String>> time_group_commands = new HashMap<>();
    public static int time_group_delay = 0;
    //Time_Group
    public static ConfigurationSection timer_group;
    public static List<String> timer_group_name = new ArrayList<>();
    public static String timer_group_timer = "";
    public static Map<String,List<String>> timer_group_commands = new HashMap<>();
    public static Map<String,List<String>> timer_group_timer_task = new HashMap<>();
    //Timer_Group
    public static ConfigurationSection timer_task;
    public static List<String> timer_task_name = new ArrayList<>();
    public static  String timer_task_timer = "";
    public static Map<String,List<String>> timer_task_commands = new HashMap<>();
    //Timer_Task
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = getConfig();

        kill_group = config.getConfigurationSection("kill-group");
        kill_group_name = config.getStringList("kill-group");

        time_group = config.getConfigurationSection("time_group");
        time_group_name = config.getStringList("time_group");

        timer_task = config.getConfigurationSection("timer-task");
        timer_task_name = config.getStringList("timer-task");

        String groups;
        for (int i = 0;i < kill_group_name.size();i++){
            groups = kill_group_name.get(i);
            kill_group_mythic_mob_id.put(groups,kill_group.getStringList(groups + ".mythic-mob-id"));
            kill_group_commands.put(groups,kill_group.getStringList(groups + ".commands"));
            kill_group_mm_time = kill_group.getInt(groups + ".mm-time");
            kill_group_time_group_name.put(groups,kill_group.getStringList(groups + ".time-group"));
        }
        //Kill_Group
        for (int i = 0;i < time_group_name.size();i++){
            groups = time_group_name.get(i);
            time_group_commands.put(groups,time_group.getStringList(groups + ".commands"));
            time_group_delay = time_group.getInt(groups + ".delay");
        }
        //Time_Group
        for (int i = 0;i < timer_group_name.size();i++){
            groups = timer_group_name.get(i);
            timer_group_timer = timer_group.getString(groups + ".timer");
            timer_group_commands.put(groups,timer_group.getStringList(groups + ".commands"));
            timer_group_timer_task.put(groups,timer_group.getStringList(groups + ".timer-task"));
        }
        //Timer_Group
        for (int i = 0;i < timer_task_name.size();i++){
            groups = timer_task_name.get(i);
            timer_task_timer = timer_task.getString(groups + ".timer");
            timer_task_commands.put(groups,timer_task.getStringList(groups + ".commands"));
        }
        //Timer_Task
        getCommand("jbsupertime").setExecutor(new JbCommandExecutor());
        getServer().getPluginManager().registerEvents(new MythicMobsKillListener(),this);
        Bukkit.getConsoleSender().sendMessage("-------JbSuperTime Running-------");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
