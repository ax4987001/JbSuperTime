package org.ax4987.jbsupertime;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

public class MythicMobsKillListener implements Listener {
    public static String mobId;
    public static String kill_group;
    public static Map<String,Double> commands;
    @EventHandler
    public void onMythicMobsKill(MythicMobDeathEvent event){
        mobId = event.getMob().getUniqueId().toString();

        for (Map.Entry<String, List<String>> entry : JbSuperTime.kill_group_mythic_mob_id.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            if (list.contains(mobId)) {
                kill_group = key;
                break;
            }
        }
        commands = RegularizationHandler.commandRegularizationHandler(JbSuperTime.kill_group_commands.get(kill_group));
        executeCommandWithProbability(commands);

    }
    public void executeCommandWithProbability(Map<String, Double> commandMap) {
        for (Map.Entry<String, Double> entry : commandMap.entrySet()) {
            double random = Math.random() * 100;
            if (random <= entry.getValue()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), entry.getKey());
            }
        }
    }
}
