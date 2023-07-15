package org.ax4987.jbsupertime;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class MythicMobsKillListener implements Listener {
    public static String mobId;
    public static String kill_group;
    @EventHandler
    public void onMythicMobsKill(MythicMobDeathEvent event){
        mobId = event.getMob().getUniqueId().toString();

        for (List<String> ids : JbSuperTime.kill_group_mythic_mob_id.values()) {
            if (ids.contains(mobId)) {
                kill_group = ids.
                break;
            }
        }
    }
}
