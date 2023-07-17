package org.ax4987.jbsupertime;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KillGroupHandler implements Listener {
    public static String mobId;
    public static String kill_group = null;
    public static List<String> time_groups;
    public static Map<String,Double> kill_group_commands;
    public static Map<String,List<String>> time_group_commands;
    public static String playerName;

    @EventHandler
    public void onMythicMobsKill(MythicMobDeathEvent event) {
        if (event.getKiller() instanceof Player) {
            mobId = event.getMob().getUniqueId().toString();
            Player player = ((Player) event.getKiller()).getPlayer();
            playerName = player.getName();
            //init

            for (Map.Entry<String, List<String>> entry : JbSuperTime.kill_group_mythic_mob_id.entrySet()) {
                String key = entry.getKey();
                List<String> list = entry.getValue();
                if (list.contains(mobId)) {
                    kill_group = key;
                    break;
                }
            }
            if (kill_group != null) {
                if (!JbSuperTime.kill_group_commands.get(kill_group).isEmpty()) {
                    kill_group_commands = RegularizationHandler.commandRegularizationHandler(JbSuperTime.kill_group_commands.get(kill_group));
                    executeCommandWithProbability(kill_group_commands);
                }
                //正则及概率

                if (!JbSuperTime.kill_group_time_group_name.isEmpty()) {
                    time_groups = JbSuperTime.kill_group_time_group_name.get(kill_group);
                    time_groups.removeIf(s -> !JbSuperTime.time_group_name.contains(s));
                    int base_delay = JbSuperTime.kill_group_mm_time.get(kill_group);
                    int delay;
                    time_group_commands.clear();
                    if (!time_groups.isEmpty()){
                        for (String s : time_groups) {
                            time_group_commands.put(s, JbSuperTime.time_group_commands.get(s));

                        }
                        for (String s : time_groups) {
                            delay = base_delay + JbSuperTime.time_group_delay.get(s);
                            TimeGroupHandler.timeGroupCommandHandler(player, time_group_commands.get(s), delay,s);
                        }
                    }
                }
                //定时组
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PlayerData playerData = new PlayerData(playerName);
        if (!playerData.getCommands().isEmpty()){
          executeCommandWithProbability(playerData.getCommands());
          playerData.setCommands(new HashMap<>());
        }
    }
    public static void executeCommandWithProbability(Map<String, Double> commandMap) {
        for (Map.Entry<String, Double> entry : commandMap.entrySet()) {
            double random = Math.random() * 100;
            if (random <= entry.getValue()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), entry.getKey());
            }
        }
    }
}
